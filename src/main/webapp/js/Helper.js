
var from = 0;
var to = 0;
var stage = false;

function displayMessage(msg, level){
    var textarea = document.getElementById("messageBox");
    if (level != 0)
        textarea.value += "********************************\n";
    textarea.value += msg + "\n";
    if (level != 0)
        textarea.value += "********************************\n";
    textarea.scrollTop = textarea.scrollHeight;
}

function addListener(){
    var classes = document.getElementsByClassName('territory');
    for (var i = 0; i < classes.length; i++) {
        classes[i].addEventListener('click', bindClick(i));
    }
}

function bindClick(id) {
    return function(){
        var classes = document.getElementsByClassName('territory');
        var div = document.createElement('div');
        idOnClick = parseInt(classes[id].getAttribute("id"));
        switch (stage) {
            case "reinforcement":
                document.getElementById("listener1").innerHTML = classes[id].title;
                if (playerTerritories.indexOf(idOnClick) != -1) {
                    console.log("Your territory");
                    document.getElementById("armyUnit").disabled = false;
                } else {
                    console.log("Not your territory");
                    document.getElementById("armyUnit").disabled = true;
                }
                break;
            case "invasion":
                if (isMyTerritory(idOnClick)) {
                    from = parseInt(idOnClick);
                    disableInput();
                    document.getElementById("listener1").innerHTML = classes[id].title;
                } else {
                    to = parseInt(idOnClick);
                    disableInput();
                    document.getElementById("listener2").innerHTML = classes[id].title;
                }
                break;
            case "fortification":
                if (isMyTerritory(idOnClick)) {
                    from = parseInt(idOnClick);
                    to = 0;
                    disableInput();
                    document.getElementById("listener1").innerHTML = classes[id].title;
                    document.getElementById("listener2").innerHTML = "";

                    var selectList = document.createElement("select");
                    selectList.className = "custom-select";
                    selectList.id = "fortify_select";
                    for (var i = 0; i < adjacent[from].length; i++) {
                        var curID = adjacent[from][i];
                        if (playerTerritories.indexOf(curID) != -1 && idOnClick != curID) {
                            var option = document.createElement("option");
                            option.value = document.getElementById(curID).id;
                            option.text = document.getElementById(curID).title;
                            selectList.appendChild(option);
                        }
                    }
                    selectList.addEventListener("click", function() {
                        to = parseInt(document.getElementById("fortify_select").value);
                        disableInput();
                    });
                    document.getElementById("listener2").appendChild(selectList);
                } else {
                    document.getElementById("listener1").innerHTML = "Choose your territory";
                    document.getElementById("listener2").innerHTML = "";
                    disableInput();
                }
                break;


        }
        console.log("you clicked region " + classes[id].title);
    };
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

function addTHRow(table, title, color) {
    var row = document.createElement("tr");
    var th = document.createElement("th");
    row.innerHTML += "<th colspan='2' bgcolor=" + color + ">" + title + "</th>";
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
    input.disabled = true;
    input.style.width = "100%";

    return (input);
}

function createDropDownBox(id, numOfDie, dest){
    var selectList = document.createElement("select");
    selectList.style.width = "75px";
    selectList.id = id;
    for (var i = 1; i <= numOfDie; i++) {
        var option = document.createElement("option");
        option.value = i;
        option.text = i;
        selectList.appendChild(option);
    }
    selectList.addEventListener("change", function() {
        dest.value = parseInt(document.getElementById(id).value);
        console.log(dest);
    });
    return selectList;
}

function isMyTerritory(tid){
    for (var i = 0; i < playerTerritories.length; i++) {
        if (tid == playerTerritories[i])
            return true;
    }
    return false;
}

function disableInput(){
    var input = document.getElementById("armyUnit");
    if (to ==0 || adjacent[from].indexOf(to) == -1) {
        input.disabled = true;
    } else {
        input.disabled = false;
    }
}

function addCards(){
    var cardNames = ["Infantry.jpg", "Cavalry.jpg", "Artillery.jpg", "wild.jpg"];
    var cardPile = document.createElement("div");
    for (var i=0; i<cards.length; i++){
        var num = cards[i];
        for (var j=0; j<num; j++){
            var elem = document.createElement("img");
            elem.setAttribute("src", "./images/" + cardNames[i]);
            elem.setAttribute("height", "35px");
            elem.setAttribute("width", "25px");
            cardPile.appendChild(elem);
        }
    }
    return cardPile;
}