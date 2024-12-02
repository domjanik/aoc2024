package Java.day2;

import Java.utils.ReadFile;

import java.util.*;

public class Day2 {
  private final String input;

  public Day2() {
    ReadFile readFile = new ReadFile();
    input = readFile.main("/Users/dominikjanik/projects/priv/aoc2024/Java/day2/input.txt");
  }


  private boolean CheckLineSafety(List<Integer> lineInput, int lineIndex, boolean useReducer) {
//    System.out.println("Line:");
//    System.out.println(lineInput);
    int margin = 3;
    boolean appliedReducer = false;
    String firstOperation;
    int startingIndex = 1;
    int firstNumber = lineInput.get(0);
    int secondNumber = lineInput.get(1);
    if (firstNumber > secondNumber) {
      if(firstNumber - secondNumber <= margin && firstNumber - secondNumber >= 1) {
        firstOperation = "DESC";
      } else if(useReducer) {
        startingIndex = 2;
        secondNumber = lineInput.get(2);
        appliedReducer = true;
        if(firstNumber - secondNumber <= margin && firstNumber - secondNumber >= 1) {
          firstOperation = "DESC";
        } else {
          return false;
        }
      } else {
//        System.out.printf("First two numbers should be DESC but are not in margin [1,3] (%d, %d)\n", firstNumber, secondNumber);
        return false;
      }
    } else if(firstNumber < secondNumber) {
      if(secondNumber - firstNumber <= margin && secondNumber - firstNumber >= 1) {
        firstOperation = "ASC";
      } else if(useReducer) {
        startingIndex = 2;
        secondNumber = lineInput.get(2);
        appliedReducer = true;
        if(secondNumber - firstNumber <= margin && secondNumber - firstNumber >= 1) {
          firstOperation = "ASC";
        } else {
          return false;
        }
      } else {
//        System.out.printf("First two numbers should be DESC but are not in margin [1,3] (%d, %d)\n", firstNumber, secondNumber);
        return false;
      }
    } else {
//        System.out.printf("First two numbers are equal (%d, %d)\n", firstNumber, secondNumber);
      return false;
    }
    boolean isSafe = true;
    for (int i = startingIndex; i < lineInput.size() - 1; i++) {
      int currNumber = lineInput.get(i);
      int nextNumber = lineInput.get(i + 1);
      if (firstOperation.equals("ASC")) {
        if (currNumber > nextNumber || nextNumber - currNumber > margin || nextNumber - currNumber < 1) {
          if(!appliedReducer && useReducer) {
            appliedReducer = true;
//            System.out.printf("Applying reducer for line %d. Current Index: %d, max Index: %d\n", lineIndex, i, lineInput.size() - 1);
            i++;
            if(i + 1 < lineInput.size()) {
              int additionalNumber = lineInput.get(i + 1);
              if (currNumber > additionalNumber || additionalNumber - currNumber > margin || additionalNumber - currNumber < 1) {
                System.out.println(lineInput);
              } else {
                continue;
              }
            } else {
              continue;
            }
          }
          isSafe = false;
//          System.out.printf("Difference should be ASC and between %d and %d is not %d or 1\n", currNumber, nextNumber, margin);
          break;
        }
      } else {
        if (currNumber < nextNumber || currNumber - nextNumber > margin || currNumber - nextNumber < 1) {
          if(!appliedReducer && useReducer) {
            appliedReducer = true;
//            System.out.printf("Applying reducer for line %d. Current Index: %d, max Index: %d\n", lineIndex, i, lineInput.size() - 1);
            i++;
            if(i + 1 < lineInput.size()) {
              int additionalNumber = lineInput.get(i + 1);
              if (currNumber < additionalNumber || currNumber - additionalNumber > margin || currNumber - additionalNumber < 1) {
                System.out.println(lineInput);
              } else {
                continue;
              }
            } else {
              continue;
            }
          }
          isSafe = false;
//          System.out.printf("Difference should be DESC and between %d and %d is not %d or 1\n", currNumber, nextNumber, margin);
          break;
        }
      }
    }
//    System.out.printf("Line is %d safe: %b\n", lineIndex, isSafe);
    return isSafe;
  }

  private void part1() {
    List<List<Integer>> fileInput = Arrays.stream(input.split("\n")).toList().stream().map((String line) -> Arrays.stream(line.split(" ")).map(Integer::new).toList()).toList();
    Integer safeLines = 0;

    for (int i = 0; i< fileInput.size(); i++) {
      boolean safety = CheckLineSafety(fileInput.get(i), i, false);
      if(safety) {
        safeLines++;
      }
    }
    System.out.printf("Safe lines on map: %d\n", safeLines);
  }

  private void part2() {
    List<List<Integer>> fileInput = Arrays.stream(input.split("\n")).toList().stream().map((String line) -> Arrays.stream(line.split(" ")).map(Integer::new).toList()).toList();
    Integer safeLines = 0;

    for (int i = 0; i< fileInput.size(); i++) {
      boolean safety = CheckLineSafety(fileInput.get(i), i, true);
      if(safety) {
        safeLines++;
      }
    }
    System.out.printf("Safe lines on map: %d\n", safeLines);
  }
  public void run() {
    part1(); //341
    part2();
  }
}
