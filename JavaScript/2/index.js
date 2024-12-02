const fs = require("fs");
const input = fs
    .readFileSync("input.txt", "utf8")
function isSafe(levels) {
    const differences = [];

    for (let i = 1; i < levels.length; i++) {
        differences.push(levels[i] - levels[i - 1]);
    }

    const increasing = differences.every((d) => d >= 1 && d <= 3);
    const decreasing = differences.every((d) => d <= -1 && d >= -3);

    return increasing || decreasing;
}

function day02() {
    const reports = input.split("\n").map((line) => line.split(" ").map(Number));

    let safe = 0;
    let madeSafe = 0;

    for (const report of reports) {
        let tolerable = false;

        for (let i = 0; i < report.length; i++) {
            const removed = [...report.slice(0, i), ...report.slice(i + 1)];

            if (isSafe(removed)) {
                tolerable = true;
                break;
            }
        }

        if (isSafe(report)) safe++;
        if (isSafe(report) || tolerable) {
            console.log(report);
            madeSafe++;
        }
    }

    return [safe, madeSafe];
}

console.log(day02());
