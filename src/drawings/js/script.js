let data = {};
let currentBlock = 0;
let currentBoard = 0;
const squareSideLength = 100

function preload() {
    data = loadJSON('../../../JsonProblem/problem.json');
}

function setup() {
    createCanvas(data['sideLength']*200, data['sideLength']*200);
}



function draw() {
    if(currentBoard < data['stepCount']) {
        fill(255, 255, 255);
        rect(0, 0, data['sideLength']*squareSideLength, data['sideLength']*squareSideLength);
        data['boards'][currentBoard]['blocks'].forEach(block => {
            if(currentBlock == 0) fill(66, 69, 244);
            else fill(214, 8, 8);
            currentBlock++;
            if(block['firstPoint']['x'] == block['secondPoint']['x']) {
                if(block['firstPoint']['y'] < block['secondPoint']['y']) {
                    rect(block['firstPoint']['y']*squareSideLength,
                        block['firstPoint']['x']*squareSideLength,
                        (block['secondPoint']['y'] - block['firstPoint']['y'] + 1)*squareSideLength,
                        squareSideLength);
                } else {
                    rect(block['secondPoint']['y']*squareSideLength,
                    block['firstPoint']['x']*squareSideLength,
                    (block['firstPoint']['y'] - block['secondPoint']['y'] + 1)*squareSideLength,
                    squareSideLength);
                }
            } else {
                if(block['firstPoint']['x'] < block['secondPoint']['x']) {
                    rect(block['firstPoint']['y']*squareSideLength,
                        block['firstPoint']['x']*squareSideLength,
                        squareSideLength,
                        (block['secondPoint']['x'] - block['firstPoint']['x'] + 1)*squareSideLength);
                } else {
                    rect(block['firstPoint']['y']*squareSideLength,
                        block['firstPoint']['x']*squareSideLength,
                        squareSideLength,
                        (block['firstPoint']['x'] - block['secondPoint']['x'] + 1)*squareSideLength);
                }
            }
        });
        currentBlock = 0;
        currentBoard++;
        wait(50);
    }
}

function wait(ms)
{
    let d = new Date();
    let d2 = null;
    do { d2 = new Date(); }
    while(d2-d < ms);
}