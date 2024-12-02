const fs = require("fs");
const fileInput = fs
  .readFileSync("input.txt", "utf8")
  .split("\n")
  .reduce(
    (prev, curr) => {
      const splitted = curr.split("   ");
      prev.left.push(splitted[0]);
      prev.right.push(splitted[1]);
      return prev;
    },
    {
      left: [],
      right: [],
    }
  );

function part1(input) {
  input.left.sort();
  input.right.sort();
  let distance = 0;
  for (let i = 0; i < input.left.length; i++) {
    distance +=
      input.left[i] > input.right[i]
        ? input.left[i] - input.right[i]
        : input.right[i] - input.left[i];
  }
  return distance;
}

function part2(input) {
  const appearenceMap = {};
  const uniqueLeft = new Set(input.left);
  for (num of uniqueLeft) {
    appearenceMap[num] = input.right.filter(
      (rightNum) => rightNum === num
    ).length;
  }

  return input.left.reduce((prev, curr) => {
    prev += appearenceMap[curr] * curr;
    return prev;
  }, 0);
}

// console.log(`Part 1 : ${part1(fileInput)}`);
console.log(`Part 2 : ${part2(fileInput)}`);
