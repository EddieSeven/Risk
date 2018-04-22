/**
 * Created by Jim on 4/20/18.
 */

function highlight(territories, color){
    if (territories.length < 1) return;
    for (var i = 0; i < territories.length; i++){
        drawPoly(document.getElementById(territories[i]).getAttribute('coords'), color);
    }
}

function getcentral(mCoords){
    var centerX = 0;
    var centerY = 0;
    for (var i = 0; i < mCoords.length; i++) {
        if(i % 2 === 0) { // index is even
            centerX += parseInt(mCoords[i]);
        } else {
            centerY += parseInt(mCoords[i]);
        }
    }
    return [2*centerX/mCoords.length, 2*centerY/mCoords.length];
}

function showText() {
    for (var i = 1; i <= 42; i++){
        drawText(document.getElementById(i).getAttribute('coords'), board[i].value, playerColor);
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
    //
    hdc.globalAlpha = 0.5;
    hdc.fillStyle = color;
    hdc.fill();
    hdc.stroke();
}

function drawText(coOrdStr, text, color){
    var mCoords = coOrdStr.split(',');
    var canvas = document.getElementById('myCanvas');
    var ctx = canvas.getContext("2d");
    ctx.fillStyle = 'black';
    ctx.font = '24px Arial Black';
    var x;
    var y;
    [x, y] = getcentral(mCoords);
    ctx.fillText(text ,x, y);
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
    var color = "PURPLE";
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
    highlight(playerTerritories, playerColor);
    showText();
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