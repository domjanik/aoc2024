package Java.day1;

import Java.utils.ReadFile;

import java.lang.reflect.Array;
import java.util.*;

public class Day1 {

  private final Day1FileInput fileInput;

  public Day1() {
    ReadFile readFile = new ReadFile();
    String input = readFile.main("day1/input.txt");
    fileInput = prepareInput(input);
  }

  private Day1FileInput prepareInput(String input) {
    ArrayList<Integer> left = new ArrayList<>();
    ArrayList<Integer> right = new ArrayList<>();
    String[] lines = input.split("\n");
    for (String line : lines) {
      List<String> parts = Arrays.stream(line.split("   ")).toList();
      left.add(Integer.parseInt(parts.get(0)));
      right.add(Integer.parseInt(parts.get(1)));
    }
    return new Day1FileInput(left, right);
  }

  private void part1() {
    List<Integer> left = fileInput.left;
    left.sort(( a, b) -> a - b);
    List<Integer> right = fileInput.right;
    right.sort(( a, b) -> a - b);
    Integer distance = 0;
    for(int i = 0; i < left.size(); i++) {
      distance += Math.abs(left.get(i) - right.get(i));
    }

    System.out.printf("[Part 1] Distance: %d\n", distance);
  }

  private void part2() {
    HashMap<Integer, Integer> appearanceMap = new HashMap<>();
    Set<Integer> uniqueLeft = new HashSet<>(fileInput.left);

    for (Integer num : uniqueLeft) {
      int count = (int) fileInput.right.stream()
          .filter(rightNum -> rightNum.equals(num))
          .count();
      appearanceMap.put(num, count);
    }

    Integer similarityScore = fileInput.left.stream()
        .reduce(0, (prev, curr) -> prev + appearanceMap.get(curr) * curr);

    System.out.printf("[Part 2] Similarity score: %d\n", similarityScore);
  }

  public void run() {
    System.out.println("------ Day 1 ------");
    part1(); // 2378066
    part2(); // 18934359
  }
}
