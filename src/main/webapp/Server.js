/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Declare Websocket to talk to server
var webSocket;


var playerName;
var playerColor;
var freeArmies;
var board = [];
var playerTerritories = [];
var fromID;
var showbox = true;

var idOnClick;

//Build URI for client side, based off of their window (OS dependent)
var loc = window.location, new_uri;
if (loc.protocol === "https:") {
    new_uri = "wss:";
} else {
    new_uri = "ws:";
}
new_uri += "//" + document.location.host + document.location.pathname;
new_uri += "risk";//Server end point reference, corrosponds to java server endpoint
//Establish connection from client URI
webSocket = new WebSocket(new_uri);
//Defualt On Open function, empty for now but can add funtionality

webSocket.onopen = function () {
    // Check onOpen Here
};

//Default on message function
//Where message recieved from server and handled
webSocket.onmessage = function (event) {
    var message = event.data;//Grab message string
    var obj = JSON.parse(message);//Turn message string into JSON

    console.log(message)
    if(obj["action"] == "result") {
        showresultStage(obj);
        return;
    }
    parseResponse(obj);
    highlight();
    var action = obj["action"];
    switch (action) {
        case "init":
            reinforceStage(freeArmies, playerName);
            break;
        case "reinforce":
            reinforceStage(freeArmies, playerName);
            break;
        case "endGame":
            endGame();
            break;
        case "continue":
            reinforceStage(freeArmies, playerName);
            break;
        case "fortify":
            endTurn();
            break;

    }

};

//Default function called when session closed
webSocket.onclose = function () {
    // Check onClose Here
};
//Default function called when error occurs (i.e 404)
webSocket.onerror = function () {
    // Check onError Here
};

function parseResponse(response){
    parsePlayer(response["player"]);
    pareseTerritories(response["territory"]);
    parseBoard(response["board"]);
}

function parsePlayer(str){
    playerName = str.split(" ")[0];
    playerColor = str.split(" ")[1];
    freeArmies = parseInt(str.split(" ")[2]);
}

function pareseTerritories(str){
    var territories = str.split(";");
    playerTerritories = [];
    for (var i = 0; i < territories.length-1; i++){
        var segs = territories[i].split(" ");
        console.log(segs);
        playerTerritories.push({
            key:   segs[0],
            value: segs[1]
        });
    }
}

function parseBoard(str){
    var territories = str.split(";");
    board = [];
    for (var i = 0; i < territories.length-1; i++){
        var segs = territories[i].split(" ");
        board.push({
            key:   segs[0],
            value: segs[1]
        });
    }
}

function registerNumber() {
    var number = document.getElementById("numOfPlayer").value;
    openTab(number);
    console.log(number);
}

function openTab(numOfBoxes) {
    cleanBoxes('controlBoxes');

    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += '<p>input your names</p>';
    div.innerHTML += "<button onclick='startGame()'> Start </button><br/>";
    for (i  = 0; i < numOfBoxes; i++){
        var newInputBox = "<input type='text' id='nameBox' name='names' class='box'/>";
        div.innerHTML += newInputBox;
    }

    document.getElementById('controlBoxes').appendChild(div);
}

function startGame(){
    addListener();

    var obj = new Object();
    addKeyValuePair(obj, 'Action', 'Init')
    var nodes = document.getElementsByName('names');
    var stArray = new Array(nodes.length);
    for (i = 0; i < nodes.length; i++){
        stArray[i] = nodes[i].value;
    }
    addKeyValuePair(obj, 'names', stArray);
    webSocket.send(JSON.stringify(obj));

    cleanBoxes('controlBoxes');
}

function reinforce(){
    var obj = new Object();

    var units = document.getElementById('armyUnit').value;
    addKeyValuePair(obj, 'Action', 'Reinforce');
    addKeyValuePair(obj, 'territoryID', parseInt(idOnClick));
    addKeyValuePair(obj, 'unitValue', parseInt(units));
    webSocket.send(JSON.stringify(obj));
}

function attack() {
    var obj = new Object();

    var units = document.getElementById('armyUnit').value;
    addKeyValuePair(obj, 'Action', 'Attack');
    addKeyValuePair(obj, 'territoryID', parseInt(fromID));
    addKeyValuePair(obj, 'targetID', parseInt(idOnClick));
    addKeyValuePair(obj, 'unitValue', parseInt(units));
    addKeyValuePair(obj, 'attackerDice', Math.min(parseInt(units), 3));
    addKeyValuePair(obj, 'defenderDice', Math.min(parseInt(board[idOnClick-1].value), 3));
    webSocket.send(JSON.stringify(obj));
}

function fortify(){
    var obj = new Object();

    var units = document.getElementById('armyUnit').value;
    addKeyValuePair(obj, 'Action', 'Fortify');
    addKeyValuePair(obj, 'fromTerritoryID', parseInt(fromID));
    addKeyValuePair(obj, 'toTerritoryID', parseInt(idOnClick));
    addKeyValuePair(obj, 'unitValue', parseInt(units));
    webSocket.send(JSON.stringify(obj));
}

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

function addListener(){
    var classes = document.getElementsByClassName('territory');
    for (var i = 0; i < classes.length; i++) {
        classes[i].addEventListener('click', bindClick(i));
    }
}

function bindClick(i) {
    return function(){
        var classes = document.getElementsByClassName('territory');
        cleanBoxes('listener');
        var div = document.createElement('div');
        div.className = 'row';
        if (showbox == true)
            div.innerHTML += "<input type='text' id='armyUnit' name='armyUnits' class='box' placeholder='input your units'>";
        idOnClick = classes[i].getAttribute("id");
        document.getElementById("listener").innerHTML += "<h5> Territory:  " + classes[i].title + " </h5>";
        console.log(idOnClick);
        console.log(board);
        document.getElementById("listener").innerHTML += "<h5> Army Force:  " + board[parseInt(idOnClick)-1].value + " </h5>";
        document.getElementById('listener').appendChild(div);
        console.log("you clicked region number " + classes[i].title);
    };
}

function reinforceStage(){
    cleanBoxes('controlBoxes');

    showbox = true;
    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p style='color:" + playerColor + ";'> Player:  " + playerName + "</p>";
    div.innerHTML += "<p>Reinforce Your Territory</p>";
    div.innerHTML += "<div class='row'><h5>Available Armies:  " + freeArmies + "</h5></div>";
    div.innerHTML += "<div class='row' id='listener'></div>";
    div.innerHTML += "<div class='row'><button onclick='reinforce()'> Reinforce </button></div>";
    div.innerHTML += "<div class='row'><button onclick='firstAttackStage()'> Next -- Attack </button></div>";
    document.getElementById('controlBoxes').appendChild(div);
}

function firstAttackStage(choice){
    cleanBoxes('controlBoxes');
    showbox = false;
    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p style='color:" + playerColor + ";'> Player:  " + playerName + "</p>";
    div.innerHTML += "<p> Where you attack from? </p>";
    div.innerHTML += "<div class='row' id='listener'></div>";
    div.innerHTML += "<div class='row'><button onclick='secondAttackStage()'> Choose This Territory</button></div>";
    div.innerHTML += "<div class='row'><button onclick='firstAttackStage()'> Reset </button></div>";
    div.innerHTML += "<div class='row'><button onclick='firstFortifyStage()'> Next -- Fortify </button></div>";
    document.getElementById('controlBoxes').appendChild(div);
}

function secondAttackStage(choice){
    cleanBoxes('controlBoxes');
    showbox = true;
    fromID = idOnClick;
    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p style='color:" + playerColor + ";'> Player:  " + playerName + "</p>";
    div.innerHTML += "<p> Where you attack to? </p>";
    div.innerHTML += "<div class='row'><h5> From Territory: </h5><h5>" + document.getElementById(idOnClick).title +
                        " with "+ board[parseInt(idOnClick)-1].value + " armies  </h5></div>";
    div.innerHTML += "<div class='row' id='listener'></div>";
    div.innerHTML += "<div class='row'><button onclick='attack()'> Conduct Attack </button></div>";
    div.innerHTML += "<div class='row'><button onclick='firstAttackStage()'> Reset </button></div>";
    div.innerHTML += "<div class='row'><button onclick='firstFortifyStage()'> Next -- Fortify </button></div>";
    document.getElementById('controlBoxes').appendChild(div);
}

function showresultStage(obj){
    cleanBoxes('controlBoxes');
    var resultStr;
    if (obj["result"] == "attacker") resultStr = "You Won";
    else resultStr = "You Lost";
    showbox = false;
    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p style='color:" + playerColor + ";'> Player:  " + playerName + "</p>";
    div.innerHTML += "<p> Attack Results</p>";
    div.innerHTML += "<div class='row'><h5>"  + resultStr + "</h5></div>";
    div.innerHTML += "<div class='row'><h5> You Lost "  + obj["attacker"] + " units </h5></div>";
    div.innerHTML += "<div class='row' id='listener'></div>";
    div.innerHTML += "<div class='row'><button onclick='firstAttackStage()'> Conduct Another Attack </button></div>";
    div.innerHTML += "<div class='row'><button onclick='firstFortifyStage()'> Next -- Fortify </button></div>";
    document.getElementById('controlBoxes').appendChild(div);
}

function firstFortifyStage(){
    cleanBoxes('controlBoxes');
    showbox = false;
    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p style='color:" + playerColor + ";'> Player:  " + playerName + "</p>";
    div.innerHTML += "<p> Where you fortify from? </p>";
    div.innerHTML += "<div class='row' id='listener'> <h5>Territory:  </h5></div>";
    div.innerHTML += "<div class='row'><button onclick='secondFortifyStage()'> Choose This Territory</button></div>";
    div.innerHTML += "<div class='row'><button onclick='firstFortifyStage()'> Reset </button></div>";
    div.innerHTML += "<div class='row'><button onclick='endTurn()'> Finish </button></div>";
    document.getElementById('controlBoxes').appendChild(div);
}

function secondFortifyStage(){
    cleanBoxes('controlBoxes');
    showbox = true;
    fromID = idOnClick;
    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p style='color:" + playerColor + ";'> Player:  " + playerName + "</p>";
    div.innerHTML += "<p> Where you fortify to? </p>";
    div.innerHTML += "<div class='row'><h5> From Territory: </h5><h5>" + document.getElementById(idOnClick).title +
        " with "+ board[parseInt(idOnClick)-1].value + " armies  </h5></div>";
    div.innerHTML += "<div class='row' id='listener'> <h5>Territory:  </h5></div>";
    div.innerHTML += "<div class='row'><button onclick='fortify()'> Fortify and Finish </button></div>";
    div.innerHTML += "<div class='row'><button onclick='endTurn()'> Finish </button></div>";
    document.getElementById('controlBoxes').appendChild(div);
}

function highlight(){
    if (playerTerritories.length < 1) return;
    for (var i = 0; i < playerTerritories.length; i++){
        drawPoly(document.getElementById(playerTerritories[i].key).getAttribute('coords'), playerColor);
    }
}

function addKeyValuePair(obj, key, value) {
    obj[key] = value;
}

function cleanBoxes(ids){
    var node = document.getElementById(ids);
    while (node.hasChildNodes()) {
        node.removeChild(node.lastChild);
    }
}

// stores the device context of the canvas we use to draw the outlines
// initialized in myInit, used in myHover and myLeave
var hdc;

// shorthand func
function byId(e){return document.getElementById(e);}

// takes a string that contains coords eg - "227,307,261,309, 339,354, 328,371, 240,331"
// draws a line from each co-ord pair to the next - assumes starting point needs to be repeated as ending point.
function drawPoly(coOrdStr, color)
{
    var mCoords = coOrdStr.split(',');
    var i, n;
    n = mCoords.length;

    hdc.beginPath();
    hdc.strokeStyle=color;
    hdc.moveTo(mCoords[0], mCoords[1]);
    for (i=2; i<n; i+=2)
    {
        hdc.lineTo(mCoords[i], mCoords[i+1]);
    }
    hdc.lineTo(mCoords[0], mCoords[1]);
    hdc.stroke();
}

function drawRect(coOrdStr)
{
    var mCoords = coOrdStr.split(',');
    var top, left, bot, right;
    left = mCoords[0];
    top = mCoords[1];
    right = mCoords[2];
    bot = mCoords[3];
    hdc.strokeRect(left,top,right-left,bot-top);
}

function myHover(element)
{
    var color = "RED";
    var coordStr = element.getAttribute('coords');
    var areaType = element.getAttribute('shape');

    switch (areaType)
    {
        case 'polygon':
        case 'poly':
            drawPoly(coordStr, color);
            break;

        case 'rect':
            drawRect(coordStr);
    }
}

function myLeave()
{
    var canvas = byId('myCanvas');
    hdc.clearRect(0, 0, canvas.width, canvas.height);
    highlight();
}

function myInit()
{
    // get the target image
    var img = byId('board-img');
    var x,y,w,h;

    x = img.offsetLeft;
    y = img.offsetTop;
    w = img.clientWidth;
    h = img.clientHeight;

    // move the canvas, so it's contained by the same parent as the image
    var imgParent = img.parentNode;
    var can = byId('myCanvas');
    imgParent.appendChild(can);

    // place the canvas in front of the image
    can.style.zIndex = 1;

    // position it over the image
    can.style.left = x+'px';
    can.style.top = y+'px';

    // make same size as the image
    can.setAttribute('width', w+'px');
    can.setAttribute('height', h+'px');

    // get it's context
    hdc = can.getContext('2d');

    // set the 'default' values for the colour/width of fill/stroke operations
    hdc.fillStyle = 'red';
    hdc.strokeStyle = 'red';
    hdc.lineWidth = 2;
}