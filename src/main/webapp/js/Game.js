
var playerName;
var playerColor;
var freeArmies;
var cards = [2,2,2,1];
var attcker_die = {value:1};
var defender_die = {value:1};

var board = [];
var adjacent = [];
var playerTerritories = [];

var idOnClick;

function sumOfArray(array){
    var sum = 0;
    for (var i = 0; i < array.length; i++) {
        sum += array[i]
    }
    return sum;
}

/* -------------------------------
 * Parsing and Querying
 * -------------------------------
 */
function readAdjacent(response){
    var adjs = response["adjLists"].split(";");
    adjacent.push("dummy");
    for (var i = 0; i < adjs.length; i++) {
        adjacent.push(adjs[i].split(" ").map(Number));
    }
}

function parseResponse(response){
    parsePlayer(response["player"]);
    parseTerritories(response["territory"]);
    parseBoard(response["board"]);
    parseCards(response["cards"]);
}

function parsePlayer(str){
    playerName = str.split(" ")[0];
    playerColor = str.split(" ")[1];
    freeArmies = parseInt(str.split(" ")[2]);
}

function parseTerritories(str){
    var territories = str.split(";");
    playerTerritories = [];
    for (var i = 0; i < territories.length-1; i++){
        var segs = territories[i].split(" ");
        playerTerritories.push(parseInt(segs[0]));
    }
}

function parseBoard(str){
    var territories = str.split(";");
    board = [];
    board.push({key:0, value:0});
    for (var i = 0; i < territories.length-1; i++){
        var segs = territories[i].split(" ");
        board.push({
            key:   parseInt(segs[0]),
            value: parseInt(segs[1])
        });
    }
}

function parseCards(str){
    var cardsArray = str.trim().split(" ");
    cards = [0, 0, 0, 0];
    for (var i = 0; i < cardsArray.length; i++){
        cards[i] = parseInt(cardsArray[i]);
    }
}
/* -------------------------------
 * Registering Player
 * -------------------------------
 */
function registerNumber() {
    // var number = document.getElementById("numOfPlayer").value;
    // addListener();
    // test();
    openTab(6);
    // console.log(number);
}

function openTab(numOfBoxes) {
    cleanBoxes('controlBoxes');

    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += '<p>Select Color by Inputting Player Names</p>';
    for (i  = 0; i < numOfBoxes; i++){
        var newInputBox = "<input type='text' id='nameBox" + i + "' name='names' class='box'/>";
        div.innerHTML += newInputBox;
    }

    div.innerHTML += "<br/><button onclick='startGame()'> Start </button><br/>";
    document.getElementById('controlBoxes').appendChild(div);
}

function getPlayers(){
    var players = [];
    var nodes = document.getElementsByName('names');
    var colorArray = new Array("GREEN", "YELLOW", "RED", "BLUE", "PINK", "GREY");
    console.log(nodes.length);
    for (var i = 0; i < nodes.length; i++){
        if (nodes[i].value.trim() !== ""){
            players.push({
                name: nodes[i].value,
                color: colorArray[i]
            });
        }
    }
    return players;
}
function claimTerritories(){
    var players = getPlayers();
    document.getElementById("messageBox").style.display="inline-block";
    cleanBoxes('controlBoxes');
    console.log(players);
    for (var i in players){
        var player = players[i];
        console.log(player);
        displayMessage(player.name + ", choose your territories!",0);
        var tableName = "controlTable";
        addTHRow(tableName, player.name + " : Chose Territory", player.color);
    }

}

/* -------------------------------
 * Starting Stage
 * -------------------------------
 */
function startGame(){
    var obj = new Object();
    addKeyValuePair(obj, 'Action', 'Init');
    var nodes = document.getElementsByName('names');
    var stArray = new Array(nodes.length);
    var playerColors = new Array();
    var colorArray = new Array("GREEN", "YELLOW", "RED", "BLUE", "PINK", "GREY");
    var nameArray = new Array();

    for (i = 0; i < nodes.length; i++){
        if (nodes[i].value !== ""){
            nameArray.push(nodes[i].value);
            playerColors.push(colorArray[i]);
        }
        stArray[i] = nodes[i].value;

    }

    addKeyValuePairWithColor(obj, 'names', nameArray, playerColors);
    console.log(obj);
    webSocket.send(JSON.stringify(obj));

    addListener();
    cleanBoxes('controlBoxes');
    document.getElementById("messageBox").style.display="inline-block";
}

/* -------------------------------
 * Reinforcement Stage
 * -------------------------------
 */
function reinforce(){
    var obj = new Object();

    var units = document.getElementById('armyUnit').value;
    addKeyValuePair(obj, 'Action', 'Reinforce');
    addKeyValuePair(obj, 'territoryID', parseInt(idOnClick));
    addKeyValuePair(obj, 'unitValue', parseInt(units));
    webSocket.send(JSON.stringify(obj));
}

function playCard(){
    var obj = new Object();
    addKeyValuePair(obj, 'Action', 'PlayCards');
    webSocket.send(JSON.stringify(obj));
}

function reinforceStage(){

    displayMessage(playerName + " is reinforcing their territories...",0);

    showText();
    cleanBoxes('controlTable');

    stage = "reinforcement";

    var tableName = "controlTable";
    addTHRow(tableName, playerName + " : Reinforce", playerColor);
    addTDRowWithButtons(tableName, createButton("REINFORCE", reinforce), createButton("NEXT STAGE", invasionStage));
    addTDRow(tableName, "Armies Remaining", freeArmies);
    // if (sumOfArray(cards) <= 2)
    //     addTDRowWithButton(tableName, "Play Cards", addCards());
    addTDRowWithButtons(tableName, createButton("PLAY CARDS", playCard), addCards());
    addTDRowWithButton(tableName, "From Location", createListener("listener1"));
    addTDRowWithButton(tableName, "Units", createInputBox());
}

/* -------------------------------
 * Invasion Stage
 * -------------------------------
 */
function invade() {
    var obj = new Object();

    var units = document.getElementById('armyUnit').value;
    addKeyValuePair(obj, 'Action', 'Attack');
    addKeyValuePair(obj, 'territoryID', parseInt(from));
    addKeyValuePair(obj, 'targetID', parseInt(to));
    addKeyValuePair(obj, 'unitValue', parseInt(units));
    addKeyValuePair(obj, 'attackerDie', parseInt(attcker_die.value));
    addKeyValuePair(obj, 'defenderDie', parseInt(defender_die.value));
    console.log(obj)
    webSocket.send(JSON.stringify(obj));
}

function invasionStage() {
    stage = "invasion";
    displayMessage(playerName + " is invading...!",0);

    cleanBoxes('controlTable');

    var tableName = "controlTable";
    addTHRow(tableName, playerName + " : Invade", playerColor);
    addTDRowWithButtons(tableName, createButton("ATTACK", invade), createButton("NEXT STAGE", fortifyStage));

    addTDRowWithButton(tableName, "From Location", createListener("listener1"));
    addTDRowWithButton(tableName, "Attacking Die", createDropDownBox("attacker_die", 3, attcker_die));
    addTDRowWithButton(tableName, "To Location", createListener("listener2"));
    addTDRowWithButton(tableName, "Defending Die", createDropDownBox("defender_die", 2, defender_die));
    addTDRowWithButton(tableName, "Units", createInputBox());
}

/* -------------------------------
 * Fortification Stage
 * -------------------------------
 */
function fortify(){
    var obj = new Object();

    var units = document.getElementById('armyUnit').value;
    addKeyValuePair(obj, 'Action', 'Fortify');
    addKeyValuePair(obj, 'fromTerritoryID', parseInt(from));
    addKeyValuePair(obj, 'toTerritoryID', parseInt(to));
    addKeyValuePair(obj, 'unitValue', parseInt(units));
    webSocket.send(JSON.stringify(obj));
}
function fortifyStage(nul){
    stage = "fortification";

    cleanBoxes('controlTable');

    var tableName = "controlTable";
    addTHRow(tableName, playerName + " : Fortify", playerColor);
    if (nul === undefined) {
        addTDRowWithButton(tableName, "End", createButton("END TURN", endTurn))
        displayMessage(playerName + " has completed fortification...",0);
    }
    else{
        displayMessage(playerName + " is fortifying...",0);
        addTDRowWithButtons(tableName, createButton("FORTIFY", fortify), createButton("END TURN", endTurn));
    }


    addTDRowWithButton(tableName, "From Location", createListener("listener1"));
    addTDRowWithButton(tableName, "To Location", createListener("listener2"));
    addTDRowWithButton(tableName, "Units", createInputBox());
}

/* -------------------------------
 * Ending Turn Stage
 * -------------------------------
 */
function endTurn(){
    var obj = new Object();

    addKeyValuePair(obj, 'Action', 'EndTurn');
    webSocket.send(JSON.stringify(obj));
}

function endGame(){
    cleanBoxes('controlBoxes');
    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p> Game Over</p>";
    document.getElementById('controlBoxes').appendChild(div);
}




function addKeyValuePair(obj, key, value) {
    obj[key] = value;
}

function addKeyValuePairWithColor(obj, key, value, color) {
    var temp = "";
    for (var i = 0; i < value.length;i++){
        temp += value[i] + " " + color[i] + ",";
    }
    temp = temp.substr(0,temp.length-1);
    obj[key] = temp;
}