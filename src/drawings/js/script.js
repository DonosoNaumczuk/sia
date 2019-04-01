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
        drawBoard();
        data['boards'][currentBoard]['blocks'].forEach(block => {
            if(currentBlock == 0) fill(66, 69, 244);
            else fill(66, 244, 191);
            currentBlock++;
            if(isLong(block)) {
                stretchesToTheRight(block) 
                ? drawRectangleStretchingToTheRight(block)
                : drawRectangleStretchingToTheLeft(block);
            } else {
                stretchesUpwards(block)
                ? drawRectangleStretchingUpwards(block)
                : drawRectangleStretchingDownwards(block);
            }
            drawExit();
        });
        currentBlock = 0;
        currentBoard++;
        wait(250);
    }
}

const wait = ms => {
    let d = new Date();
    let d2 = null;
    do { d2 = new Date(); }
    while(d2-d < ms);
}

const drawBoard = () => {
    fill(255, 255, 255);
    rect(0, 0, data['sideLength']*squareSideLength, data['sideLength']*squareSideLength);
}
const drawExit = () => {
    fill(224, 4, 19);
    rect((data['exit']['y']+1)*squareSideLength,
        data['exit']['x']*squareSideLength,
        0.25*squareSideLength,
        squareSideLength);
}
const isLong = block => block['firstPoint']['x'] == block['secondPoint']['x'];

const stretchesToTheRight = block => block['firstPoint']['y'] < block['secondPoint']['y'];

const stretchesUpwards = block => block['firstPoint']['x'] < block['secondPoint']['x'];

const drawRectangleStretchingToTheRight = block => rect(block['firstPoint']['y']*squareSideLength,
                                                        block['firstPoint']['x']*squareSideLength,
                                                        (block['secondPoint']['y'] - block['firstPoint']['y'] + 1)*squareSideLength,
                                                        squareSideLength);

const drawRectangleStretchingToTheLeft = block => rect(block['secondPoint']['y']*squareSideLength,
                                                        block['firstPoint']['x']*squareSideLength,
                                                        (block['firstPoint']['y'] - block['secondPoint']['y'] + 1)*squareSideLength,
                                                        squareSideLength);

const drawRectangleStretchingUpwards = block => rect(block['firstPoint']['y']*squareSideLength,
                                                    block['firstPoint']['x']*squareSideLength,
                                                    squareSideLength,
                                                    (block['secondPoint']['x'] - block['firstPoint']['x'] + 1)*squareSideLength);

const drawRectangleStretchingDownwards = block => rect(block['firstPoint']['y']*squareSideLength,
                                                        block['firstPoint']['x']*squareSideLength,
                                                        squareSideLength,
                                                        (block['firstPoint']['x'] - block['secondPoint']['x'] + 1)*squareSideLength);