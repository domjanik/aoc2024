package Java.day7;

import Java.utils.ReadFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Day7Input {

  Long result;
  List<Integer> numbers;

  public Day7Input(Long result, List<Integer> numbers) {
    this.result = result;
    this.numbers = numbers;
  }
}

public class Day7 {
  private final List<Day7Input> fileInput;

  public Day7() {
    ReadFile readFile = new ReadFile();
    fileInput = Arrays.stream(readFile.main("day7/input.txt").split("\n"))
        .map(e -> {
          List<String> parts = Arrays.asList(e.split(":"));

          return new Day7Input(
              Long.parseLong(parts.get(0)),
              Arrays.stream(parts.get(1).split(" "))
                  .filter(n -> !n.isEmpty())
                  .map(Integer::parseInt)
                  .collect(Collectors.toList())
          );
        })
        .toList();
  }

  private boolean isResultAvailable(Long result, List<Integer> numbers, List<String> operators) {
    List<Long> results = new ArrayList<>();
    results.add((long) numbers.get(0));

    for (int i = 1; i < numbers.size(); i++) {
      List<Long> newResults = new ArrayList<>();
      for (Long r : results) {
        for (String operator : operators) {
          switch (operator) {
            case "+" -> newResults.add(r + numbers.get(i));
            case "*" -> newResults.add(r * numbers.get(i));
            case "||" -> newResults.add(Long.parseLong(r.toString() + numbers.get(i).toString()));
          }
        }
      }

      results = newResults;
    }

    return results.contains(result);
  }

  private void part1() {
    long sumValue = 0;
    for (Day7Input input : fileInput) {
      boolean isResultAvailable = isResultAvailable(input.result, input.numbers, List.of("+", "*"));
      if (isResultAvailable) {
        sumValue += input.result;
      }
    }

    System.out.printf("[Part 1]: Result of valid equations is %d ", sumValue);
  }

  private void part2() {
    long sumValue = 0;
    for (Day7Input input : fileInput) {
      boolean isResultAvailable = isResultAvailable(input.result, input.numbers, List.of("+", "*", "||"));
      if (isResultAvailable) {
        sumValue += input.result;
      }
    }

    System.out.printf("[Part 2]: Result of valid equations is %d \n", sumValue);
  }

  public void run() {
    System.out.println("------ Day 7 ------");
    part1();
    part2();
  }
}
