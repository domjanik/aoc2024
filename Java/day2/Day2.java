package Java.day2;

import Java.utils.ReadFile;

import java.util.*;

public class Day2 {

  private final String input;

  public Day2() {
    ReadFile readFile = new ReadFile();
    input = readFile.main("/Users/dominikjanik/projects/priv/aoc2024/Java/day2/input.txt");
  }

  private boolean CheckLineSafety(List<Integer> lineInput) {
    boolean isSafe = true;
    ArrayList<Integer> diffs = new ArrayList<>();
    for (int i = 0; i < lineInput.size() - 1; i++) {
      diffs.add(lineInput.get(i) - lineInput.get(i + 1));
    }
    String order = diffs.get(0) > 0 ? "asc" : "desc";

    for (Integer diff : diffs) {
      if (order.equals("asc") && (diff < 1 || diff > 3)) {
        isSafe = false;
        break;
      } else if (order.equals("desc") && (diff < -3 || diff > -1)) {
        isSafe = false;
        break;
      }
    }
    return isSafe;
  }

  private void part1() {
    List<List<Integer>> fileInput = prepareInputArray();
    Integer safeLines = 0;

    for (List<Integer> integers : fileInput) {
      boolean safety = CheckLineSafety(integers);
      if (safety) {
        safeLines++;
      }
    }
    System.out.printf("[Part 1] Safe lines on map: %d\n", safeLines);
  }

  private void part2() {
    List<List<Integer>> fileInput = prepareInputArray();
    Integer safeLines = 0;

    for (List<Integer> integers : fileInput) {
      boolean safety = CheckLineSafety(integers);
      if (!safety) {
        for (int i = 0 ; i < integers.size(); i++) {
          List<Integer> subList = new ArrayList<>(integers);
          subList.remove(i);
          safety = CheckLineSafety(subList);
          if (safety) {
            break;
          }
        }
      }
      if (safety) {
        safeLines++;
      }
    }
    System.out.printf("[Part 2] Safe lines on map: %d\n", safeLines);
  }

  private List<List<Integer>> prepareInputArray() {
    return Arrays.stream(input.split("\n"))
        .toList()
        .stream()
        .map((String line) -> Arrays.stream(line.split(" ")).map(Integer::new).toList())
        .toList();
  }

  public void run() {
    part1();
    part2();
  }
}
