package Java.day11;

import Java.utils.ReadFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 {

  private final List<Long> fileInput;

  public Day11() {
    ReadFile readFile = new ReadFile();
    fileInput = Arrays.stream(readFile.main("day11/input.txt").split(" ")).map(num -> Long.parseLong(num.trim())).toList();
  }

  private List<Long> applyBlinkingRule(Long number) {
    if(number == 0) {
      return List.of(1L);
    }
    int length = (int) Math.log10(number) + 1;
    if(length % 2 == 0) {
      int halfLength = length / 2;
      long divisor = (long) Math.pow(10, halfLength);
      long firstHalf = number / divisor;
      long secondHalf = number % divisor;

      return List.of(firstHalf, secondHalf);
    }
    return List.of(number * 2024);
  }

  private List<Long> reorderStones(List<Long> stoneList, int reordersToMake) {
    List<Long> stoneOrder = stoneList;

    for (int i = 0; i < reordersToMake; i++) {
      System.out.printf("Reordering stones for the %d time\n", i + 1);
      ArrayList<Long> newStoneOrder = new ArrayList<>();
      for (long stone : stoneOrder) {
        newStoneOrder.addAll(applyBlinkingRule(stone));
      }

      stoneOrder = newStoneOrder;
    }

    return stoneOrder;
  }

  private void part1() {
    int blinksToMake = 25;
    List<Long> stoneList = reorderStones(fileInput, blinksToMake);
    System.out.printf("[Part 1] New stone array size: %d \n", stoneList.size());
  }

  private void part2() {
    int blinksToMake = 75;

    List<Long> stoneList = reorderStones(fileInput, blinksToMake);

    System.out.printf("[Part 2] New stone array size: %d \n", stoneList.size());
  }

  public void run() {
    System.out.println("------ Day 11 ------");
//    part1();
    part2();
  }
}
