package Java.day3;

import Java.utils.ReadFile;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

  private final String fileInput;

  public Day3() {
    ReadFile readFile = new ReadFile();
    fileInput = readFile.main("day3/input.txt");
  }

  private void part1() {
    int result = 0;
    String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(fileInput);

    while (matcher.find()) {
      List<Integer> numbers = Arrays.stream(matcher.group()
          .replace("mul(", "")
          .replace(")", "")
          .split(","))
          .map(Integer::parseInt).toList();

      result += numbers.get(0) * numbers.get(1);
    }

    System.out.printf("[Part 1] Multiply result: %d\n", result);
  }

  private void part2() {
    int result = 0;
    String regex = "(don't\\(\\))|(do\\(\\))|(mul\\(\\d{1,3},\\d{1,3}\\))";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(fileInput);
    boolean turnedOn = true;

    while (matcher.find()) {
      if(matcher.group().contains("do()"))
        turnedOn = true;
      else if(matcher.group().contains("don't()"))
        turnedOn = false;
      else if(turnedOn) {
        List<Integer> numbers = Arrays.stream(matcher.group()
                                                  .replace("mul(", "")
                                                  .replace(")", "")
                                                  .split(","))
            .map(Integer::parseInt).toList();
        result += numbers.get(0) * numbers.get(1);
      }
    }

    System.out.printf("[Part 2] Multiply result: %d\n", result);
  }

  public void run() {
    System.out.println("------ Day 3 ------");
    part1();
    part2();
  }
}
