package Java.day4;

import Java.utils.ReadFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum Direction {
  UP, DOWN, LEFT, RIGHT,
  UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT
}

public class Day4 {

  private final List<List<String>> fileInput;

  private final List<String> XMAS = new ArrayList<>();

  public Day4() {
    ReadFile readFile = new ReadFile();
    fileInput = Arrays.stream(readFile
                                  .main("day4/input.txt")
                                  .split("\n"))
        .map(e -> Arrays.asList(e.split(""))).toList();
    XMAS.add("X");
    XMAS.add("M");
    XMAS.add("A");
    XMAS.add("S");
  }

  private boolean findNextLetter(int i, int j, String letter, Direction direction) {
    if (i == fileInput.size() || i < 0 || j < 0 || j == fileInput.get(i).size()) {
      return false;
    }

    if (fileInput.get(i).get(j).equals(letter) && letter.equals("S")) {
      return true;
    }

    if (fileInput.get(i).get(j).equals(letter)) {
      int index = XMAS.indexOf(letter) + 1;
      if (direction == Direction.LEFT) {
        return findNextLetter(i, j - 1, XMAS.get(index), Direction.LEFT);
      } else if (direction == Direction.RIGHT) {
        return findNextLetter(i, j + 1, XMAS.get(index), Direction.RIGHT);
      } else if (direction == Direction.UP) {
        return findNextLetter(i - 1, j, XMAS.get(index), Direction.UP);
      } else if (direction == Direction.DOWN) {
        return findNextLetter(i + 1, j, XMAS.get(index), Direction.DOWN);
      } else if (direction == Direction.UPLEFT) {
        return findNextLetter(i - 1, j - 1, XMAS.get(index), Direction.UPLEFT);
      } else if (direction == Direction.UPRIGHT) {
        return findNextLetter(i - 1, j + 1, XMAS.get(index), Direction.UPRIGHT);
      } else if (direction == Direction.DOWNLEFT) {
        return findNextLetter(i + 1, j - 1, XMAS.get(index), Direction.DOWNLEFT);
      } else if (direction == Direction.DOWNRIGHT) {
        return findNextLetter(i + 1, j + 1, XMAS.get(index), Direction.DOWNRIGHT);
      }

      return findNextLetter(i, j, XMAS.get(index), direction);
    }

    return false;
  }

  private void part1() {
    int xmasCount = 0;
    for (int i = 0; i < fileInput.size(); i++) {
      List<String> line = fileInput.get(i);
      for (int j = 0; j < line.size(); j++) {
        if (line.get(j).equals("X")) {
          if (findNextLetter(i, j - 1, XMAS.get(1), Direction.LEFT)) {
            xmasCount++;
          }
          if (findNextLetter(i, j + 1, XMAS.get(1), Direction.RIGHT)) {
            xmasCount++;
          }
          if (findNextLetter(i - 1, j, XMAS.get(1), Direction.UP)) {
            xmasCount++;
          }
          if (findNextLetter(i + 1, j, XMAS.get(1), Direction.DOWN)) {
            xmasCount++;
          }
          if (findNextLetter(i - 1, j - 1, XMAS.get(1), Direction.UPLEFT)) {
            xmasCount++;
          }
          if (findNextLetter(i - 1, j + 1, XMAS.get(1), Direction.UPRIGHT)) {
            xmasCount++;
          }
          if (findNextLetter(i + 1, j - 1, XMAS.get(1), Direction.DOWNLEFT)) {
            xmasCount++;
          }
          if (findNextLetter(i + 1, j + 1, XMAS.get(1), Direction.DOWNRIGHT)) {
            xmasCount++;
          }
        }
      }
    }
    System.out.printf("[Part 1] There is %d xmasses in input\n", xmasCount);
  }

  private boolean findCrossedMas(int i, int j) {
    List<String> prevLine = fileInput.get(i - 1);
    List<String> nextLine = fileInput.get(i + 1);
    if((prevLine.get(j-1).equals("M") && nextLine.get(j+1).equals("S") ||
        prevLine.get(j-1).equals("S") && nextLine.get(j+1).equals("M")) &&
        (prevLine.get(j+1).equals("M") && nextLine.get(j-1).equals("S") ||
         prevLine.get(j+1).equals("S") && nextLine.get(j-1).equals("M"))) {
      return true;
    } else {
      return false;
    }
  }

  private void part2() {
    int xmasCount = 0;
    for (int i = 1; i < fileInput.size() - 1; i++) {
      List<String> line = fileInput.get(i);
      for (int j = 1; j < line.size() - 1; j++) {
        if (line.get(j).equals("A")) {
          if(findCrossedMas(i, j)) {
                xmasCount++;
          }
        }
      }
    }
    System.out.printf("[Part 2] There is %d xmasses in input\n", xmasCount);
  }

  public void run() {
    System.out.println("------ Day 4 ------");
    part1();
    part2();
  }
}
