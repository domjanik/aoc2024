package Java.day5;

import Java.utils.ReadFile;

import java.util.*;

class Rule {

  HashSet<Integer> prev = new HashSet<>();
  HashSet<Integer> next = new HashSet<>();

  @Override
  public String toString() {
    return String.format("Prev: %s, Next: %s", prev.toString(), next.toString());
  }
}

public class Day5 {

  private final List<List<Integer>> fileInput;
  private final Map<Integer, Rule> rules = new HashMap<>();

  public Day5() {
    ReadFile readFile = new ReadFile();
    fileInput = Arrays.stream(readFile
                                  .main("day5/instructions.txt")
                                  .split("\n"))
        .map(arr -> Arrays.stream(arr.split(",")).map(Integer::parseInt).toList()).toList();
    Arrays.stream(readFile
                      .main("day5/input.txt")
                      .split("\n"))
        .forEach(rule -> {
          Integer[] parts = Arrays.stream(rule.split("\\|")).map(Integer::parseInt).toArray(Integer[]::new);
          if (!rules.containsKey(parts[0])) {
            rules.put(parts[0], new Rule());
          }
          if (!rules.containsKey(parts[1])) {
            rules.put(parts[1], new Rule());
          }
          rules.get(parts[0]).next.add(parts[1]);
          rules.get(parts[1]).prev.add(parts[0]);
        });
  }

  private boolean checkIfValid(List<Integer> line) {
    for (int i = 0; i < line.size(); i++) {
      Rule rule = rules.get(line.get(i));
      int startIndex = i > 0 ? i - 1 : 0;
      List<Integer> prevList = line.subList(0, startIndex);
      List<Integer> nextList = line.subList(i + 1, line.size());

      if (!rule.prev.containsAll(prevList) || !rule.next.containsAll(nextList)) {
        return false;
      }
    }
    return true;
  }

  private List<Integer> sortInvalidLine(List<Integer> line) {
    ArrayList<Integer> sortedList = new ArrayList<>();

    for (Integer num : line) {
      Rule rule = rules.get(num);
      int index = sortedList.size();
      if (index == 0) {
        sortedList.add(num);
        continue;
      }
      for (Integer nextNum : rule.next) {
        if (sortedList.contains(nextNum)) {
          Integer nextNumIndex = sortedList.indexOf(nextNum);
          if (nextNumIndex < index) {
            index = nextNumIndex;
          }
        }
      }
      sortedList.add(index, num);
    }

    return sortedList;
  }

  private void part1() {
    int sum = 0;

    for (List<Integer> line : fileInput) {
      boolean isValid = checkIfValid(line);
      if (isValid) {
        Integer midNumber = line.get((int) Math.ceil(line.size() / 2));

        sum += midNumber;
      }
    }

    System.out.printf("[Part 1] Summed value of mid numbers: %d \n", sum);
  }

  private void part2() {
    int sum = 0;

    for (List<Integer> line : fileInput) {
      boolean isValid = checkIfValid(line);
      if (!isValid) {
        List<Integer> sortedLine = sortInvalidLine(line);
        Integer midNumber = sortedLine.get((int) Math.ceil(sortedLine.size() / 2));

        sum += midNumber;
      }
    }

    System.out.printf("[Part 2] Summed value of mid numbers: %d \n", sum);
  }

  public void run() {
    System.out.println("------ Day 5 ------");
    part1();
    part2();
  }
}
