/**
 * Created by Jim on 4/20/18.
 */

function addListener(){
    var classes = document.getElementsByClassName('territory');
    for (var i = 0; i < classes.length; i++) {
        classes[i].addEventListener('click', bindClick(i));
    }
}

function bindClick(i) {
    return function(){
        var classes = document.getElementsByClassName('territory');
        var div = document.createElement('div');
        idOnClick = classes[i].getAttribute("id");
        if (true) {
            document.getElementById("listener1").innerHTML = classes[i].title;
            document.getElementById("listener1").appendChild(div);
        }
        else {
            document.getElementById("listener2").innerHTML = classes[i].title;
            document.getElementById("listener2").appendChild(div);
        }
        console.log("you clicked region number " + classes[i].title);
    };
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

function addTDRowWithButton(table, left, right) {
    var row = document.createElement("tr");
    row.innerHTML += "<td>" + left + "</td>"
    var col2 = document.createElement("td");
    col2.appendChild(right);
    row.appendChild(col2);
    document.getElementById(table).appendChild(row);
}

function addTDRowWithButtons(table, left, right) {
    var row = document.createElement("tr");
    var col1 = document.createElement("td");
    col1.appendChild(left);
    var col2 = document.createElement("td");
    col2.appendChild(right);
    row.appendChild(col1);
    row.appendChild(col2);
    document.getElementById(table).appendChild(row);
}

function addTDRow(table, left, right) {
    var row = document.createElement("tr");
    row.innerHTML += "<td>" + left + "</td>" +
        "<td>" + right + "</td>";
    document.getElementById(table).appendChild(row);
}

function addTHRow(table, title) {
    var row = document.createElement("tr");
    row.innerHTML += "<th colspan='2'>" + title + "</th>";
    document.getElementById(table).appendChild(row);
}

function createButton(text, f) {
    var btn = document.createElement("button");
    btn.onclick = f;
    btn.innerHTML = text;
    return (btn);
}

function createListener(id){
    var div = document.createElement("div");
    div.id = id;
    div.innerHTML += "Click To Show"
    return (div);
}

function createInputBox(){
    var input = document.createElement("input");
    input.type = "text";
    input.id = "armyUnit";
    input.placeholder = "input your units";
    input.style.width = "100%";
    input.disabled="disabled";
    return (input);
}