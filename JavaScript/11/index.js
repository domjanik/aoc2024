const fs = require("fs");
const fileInput = fs
    .readFileSync("input.txt", "utf8")
    .split(" ")
    .map((num) => num.replace("\n", ""));

function applyRule(num) {
    if(num === '0') {
        return ['1'];
    }
    if(num.length % 2 === 0) {
        const partOne = num.slice(0, num.length / 2);
        const partTwo = num.slice(num.length / 2, num.length);
        return [partOne, partTwo];
    }
    return [(num * 2024).toString()]
}

function reorderStones(stones) {
    const newStones = [];
    for(let i = 0; i < stones.length; i++) {
        newStones.push(...applyRule(stones[i]));
    }
    return newStones;
}

function part1(input) {
    const blinksToMake = 25;
    let stones = input;
    for(let i = 0; i < blinksToMake; i++) {
        stones = reorderStones(stones);
    }
    return stones.length;
}


function reorderRec(stones, blink, desiredBlink) {
    if(blink === desiredBlink) {
        return stones.length;
    }
    let score = 0;

    for(let i = 0; i < stones.length; i++) {
        let resStones = applyRule(stones[i]); // [1]/[2,3]/[n*2024]
        score += reorderRec(resStones, blink + 1, desiredBlink);
    }

    return score;
}

function part2(input) {
    console.log("Started at ", new Date());
    const blinksToMake = 75;
    return reorderRec(input, 1, blinksToMake);
}

console.log(`Part 1 : ${part1(fileInput)}`);
console.log(`Part 1 : ${part2(fileInput)}`);
