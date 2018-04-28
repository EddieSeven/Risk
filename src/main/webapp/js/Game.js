
var playerName;
var playerColor;
var freeArmies;
var availablecards;

var board = [];
var adjacent = [];
var playerTerritories = [];

var idOnClick;

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
    console.log("adjacent");
    console.log(adjacent);
}

function parseResponse(response){
    parsePlayer(response["player"]);
    parseTerritories(response["territory"]);
    parseBoard(response["board"]);
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
    console.log("playerTerritories");
    console.log(playerTerritories);
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
    for (i  = 0; i < 6; i++){
        var newInputBox = "<input type='text' id='nameBox" + i + "' name='names' class='box'/>";
        div.innerHTML += newInputBox;
    }



    div.innerHTML += "<br/><button onclick='startGame()'> Start </button><br/>";
    document.getElementById('controlBoxes').appendChild(div);
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

function reinforceStage(){

    displayMessage(playerName + "'s turn to conduct reinforcement...");

    showText();
    cleanBoxes('controlTable');

    stage = "reinforcement";

    var tableName = "controlTable";
    addTHRow(tableName, playerName + " : Reinforce", playerColor);
    addTDRowWithButtons(tableName, createButton("REINFORCE", reinforce), createButton("NEXT STAGE", invasionStage));
    addTDRow(tableName, "Free Armies", freeArmies);
    addTDRow(tableName, "Free Cards", availablecards);
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
    addKeyValuePair(obj, 'attackerDice', Math.min(parseInt(units), 3));
    addKeyValuePair(obj, 'defenderDice', Math.min(parseInt(board[idOnClick].value), 2));
    webSocket.send(JSON.stringify(obj));
}

function invasionStage() {
    stage = "invasion";
    displayMessage(playerName + "'s turn to conduct invasion...");

    cleanBoxes('controlTable');



    var tableName = "controlTable";
    addTHRow(tableName, playerName + " : Invade", playerColor);
    addTDRowWithButtons(tableName, createButton("ATTACK", invade), createButton("NEXT STAGE", fortifyStage));

    addTDRowWithButton(tableName, "From Location", createListener("listener1"));
    addTDRowWithButton(tableName, "To Location", createListener("listener2"));
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
    console.log(nul, "  ", nul instanceof MouseEvent);
    stage = "fortification";

    cleanBoxes('controlTable');

    var tableName = "controlTable";
    addTHRow(tableName, playerName + " : Fortify", playerColor);
    if (nul === undefined) {
        addTDRowWithButton(tableName, "End", createButton("END TURN", endTurn))
        displayMessage(playerName + " has run out of fortification chances...");
    }
    else{
        displayMessage(playerName + "'s turn to conduct fortification...");
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
    div.innerHTML += "<p> Game Ends</p>";
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