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

};

//Default function called when session closed
webSocket.onclose = function () {
    // Check onClose Here
};
//Default function called when error occurs (i.e 404)
webSocket.onerror = function () {
    // Check onError Here
};


function registerNumber() {
    var number = document.getElementById("numOfPlayer").value;
    openTab(number);
    console.log(number);
}

function openTab(numOfBoxes) {
    var node = document.getElementById('nameBoxes');
    while (node.hasChildNodes()) {
        node.removeChild(node.lastChild);
    }

    var div = document.createElement('div');
    div.className = 'row';
    div.innerHTML += '<p>input your names</p>';
    div.innerHTML += "<button onclick='startGame()'> Submit </button><br/>";
    for (i  = 0; i < numOfBoxes; i++){
        var newInputBox = "<input type='text' id='nameBox' name='names' class='box'/>";
        div.innerHTML += newInputBox;
    }

    document.getElementById('startPanel').style.display = 'none';
    document.getElementById('nameBoxes').appendChild(div);
}

function startGame(){
    var obj = new Object();
    addKeyValuePair(obj, 'Action', 'Init')
    var nodes = document.getElementsByName('names');
    var stArray = new Array(nodes.length);
    for (i = 0; i < nodes.length; i++){
        stArray[i] = nodes[i].value;
    }
    addKeyValuePair(obj, 'names', stArray);
    webSocket.send(JSON.stringify(obj));
    console.log(stArray);
}

function addKeyValuePair(obj, key, value) {
    obj.Key = key;//Set Key of JSON
    obj.Value = value;//Set Filename value
}