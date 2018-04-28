
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

    switch (obj["action"]) {
        case "open":
            readAdjacent(obj);
            break;
        case "init":
            parseResponse(obj);
            highlight(playerTerritories, playerColor);
            reinforceStage();
            break;
        case "reinforce":
            if (parseInt(obj["player"].split(" ")[2]) != freeArmies)
                displayMessage(playerName + " successfully reinforced");
            parseResponse(obj);
            highlight(playerTerritories, playerColor);
            reinforceStage();
            break;
        case "continue":
            parseResponse(obj);
            highlight(playerTerritories, playerColor);
            myLeave();
            reinforceStage();
            break;
        case "fortify":
            endTurn();
            break;
        case "endGame":
            endGame();
            break;
        case "result":
            if (obj["result"] == "attacker") displayMessage("RESULT: You Won");
            else displayMessage("RESULT: You Lost");
            displayMessage("You lost " + obj["attacker"] + " armies");
            displayMessage("The defender lost " + obj["defender"] + " armies");
            break;
        case "error":
            displayMessage(obj["error"]);
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