let data = {};
let levels = [];
let i = 0;
const squareSideLength = 100

function preload() {
  levels.push(loadJSON('../../../boardsJSON/level1.json'));
}

function setup() {
  createCanvas(data['boardLength']*200, data['boardLength']*200);
}



function draw() {
    fill(255, 255, 255);
    rect(0, 0, data['boardLength']*squareSideLength, data['boardLength']*squareSideLength);
    levels[0]['blocks'].forEach(block => {
        if(i == 0) fill(66, 69, 244);
        else fill(214, 8, 8);
        i++;
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
    i = 0;
}