/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Declare Websocket to talk to server
var webSocket;
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
    // reinforceStage(10, 'Helleo');

};

//Default function called when session closed
webSocket.onclose = function () {
    // Check onClose Here
};
//Default function called when error occurs (i.e 404)
webSocket.onerror = function () {
    // Check onError Here
};


var idOnClick;
var playerName;
var freeArmies;

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
    cleanBoxes('controlBoxes');
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


}

function reinforce(){
    var obj = new Object();

    var units = document.getElementById('armyUnit').value;
    addKeyValuePair(obj, 'Action', 'Reinforce');
    addKeyValuePair(obj, 'territoryID', idOnClick);
    addKeyValuePair(obj, 'unitValue', units);
    webSocket.send(JSON.stringify(obj));
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
        div.innerHTML += "<input type='text' id='armyUnit' name='armyUnits' class='box' placeholder='input your units'>";
        idOnClick = i;
        document.getElementById("listener").innerHTML += "<h3> Territory:  " + classes[i].title + " </h3>";
        document.getElementById('listener').appendChild(div);
        console.log("you clicked region number " + classes[i].title);
    };
}

function reinforceStage(){
    cleanBoxes('controlBoxes');


    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p> Player:  " + playerName + "</p>";
    div.innerHTML += "<p>Reinforce Your Territory</p>";
    div.innerHTML += "<div class='row'><h3>Available Armies:  " + freeArmies + "</h3></div>";
    div.innerHTML += "<div class='row' id='listener'> <h3>Territory:  </h3></div>";
    div.innerHTML += "<div class='row'><button onclick='reinforce()'> Reinforce </button></div>";
    div.innerHTML += "<div class='row'><button onclick='attackStage()'> Next </button></div>";
    document.getElementById('controlBoxes').appendChild(div);
}

function attackStage(){
    cleanBoxes('controlBoxes');


    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += "<p> Player:  " + playerName + "</p>";
    div.innerHTML += "<p>Reinforce Your Territory</p>";
    div.innerHTML += "<div class='row'><h3>Available Armies:  " + freeArmies + "</h3></div>";
    div.innerHTML += "<div class='row' id='listener'> <h3>Territory:  </h3></div>";
    div.innerHTML += "<div class='row'><button onclick='reinforce()'> Reinforce </button></div>";
    div.innerHTML += "<div class='row'><button onclick='attackStage()'> Next </button></div>";
    document.getElementById('controlBoxes').appendChild(div);
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

