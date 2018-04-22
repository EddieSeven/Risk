
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
    console.log("board");
    console.log(board);
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

function test(){
    showText();
    cleanBoxes('controlBoxes');

    showbox = true;

    var tableName = "playerTable";
    addTDRow(tableName, "Player", "CD");
    addTDRow(tableName, "Color", "Yellow");
    addTDRow(tableName, "Available Armies", "11");
    addTDRow(tableName, "Cards", "11");


    var tableName = "controlTable";

    addTHRow(tableName, "Player 1 : Fortify", "yellow");
    addTDRowWithButtons(tableName, createButton("REINFORCE", reinforce), createButton("NEXT STAGE", reinforceStage));

    // addTDRow(tableName, "From", "<div class='row' id='listener'></div>");
    addTDRowWithButton(tableName, "From Location", createListener("listener1"));
    // addTDRowWithButton(tableName, "To Location", createListener("listener2"));
    addTDRowWithButton(tableName, "Units", createInputBox());

    var tableName = "controlButtons";
    addTDRowWithButtons(tableName, createButton("REINFORCE", reinforce), createButton("NEXT STAGE", reinforceStage));
}

/* -------------------------------
 * Starting Stage
 * -------------------------------
 */
function startGame(){
    var obj = new Object();
    var counter = 0;
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

    showText();
    cleanBoxes('controlTable');

    stage = "reinforcement";

    var tableName = "controlTable";
    addTHRow(tableName, playerName + " : Reinforce", playerColor);
    addTDRowWithButtons(tableName, createButton("REINFORCE", reinforce), createButton("NEXT STAGE", invasionStage));

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
    addKeyValuePair(obj, 'defenderDice', Math.min(parseInt(board[idOnClick].value), 3));
    webSocket.send(JSON.stringify(obj));
}

function invasionStage() {
    cleanBoxes('controlTable');

    stage = "invasion";

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

function fortifyStage(){
    cleanBoxes('controlTable');

    stage = "fortification";

    var tableName = "controlTable";
    addTHRow(tableName, playerName + " : Fortify", playerColor);
    addTDRowWithButtons(tableName, createButton("FORTIFY", fortify), createButton("END TURN", endTurn));

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


function showresultStage(obj){
    cleanBoxes('controlBoxes');
    var resultStr;
    if (obj["result"] == "attacker") resultStr = "You Won";
    else resultStr = "You Lost";
    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p style='color:" + playerColor + ";'> Player:  " + playerName + "</p>";
    div.innerHTML += "<p> Attack Results</p>";
    div.innerHTML += "<div class='row>";
    div.innerHTML += "<button onclick='firstAttackStage()'> Conduct Another Attack </button>";
    div.innerHTML += "<button onclick='firstFortifyStage()'> Next -- Fortify </button>";
    div.innerHTML += "</div>";
    div.innerHTML += "<div class='row'><h5>"  + resultStr + "</h5></div>";
    div.innerHTML += "<div class='row'><h5> You Lost "  + obj["attacker"] + " units </h5></div>";
    div.innerHTML += "<div class='row' id='listener'></div>";
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