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
        drawGrid();
        data['boards'][currentBoard]['blocks'].forEach(block => {
            if(currentBlock == 0) fill(66, 69, 244);
            else fill(66, 244, 191);
            currentBlock++;
            if(isLong(block)) {
                stretchesToTheRight(block)
                ? drawRectangleStretchingToTheRight(block)
                : drawRectangleStretchingToTheLeft(block);
            } else {
                if (block['firstPoint']['x'] < block['secondPoint']['x']) {
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
            drawExit(224, 4, 19);
        });
        currentBlock = 0;
        currentBoard++;
        wait(50);
    } else {
        drawExit(72, 237, 7);
    }
}

const wait = ms => {
    let d = new Date();
    let d2 = null;
    do { d2 = new Date(); }
    while(d2-d < ms);
}

const drawGrid = () => {
    fill(255, 255, 255);
    rect(0, 0, data['sideLength']*squareSideLength, data['sideLength']*squareSideLength);
    for(i = 0; i < data['sideLength']; i++) {
        line(0, squareSideLength*i, squareSideLength*data['sideLength'], squareSideLength*i);
        line(squareSideLength*i, 0, squareSideLength*i, squareSideLength*data['sideLength']);
    }
}
const drawExit = (r, g, b) => {
    fill(r, g, b);
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
