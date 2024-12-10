package Java.day10;

import Java.utils.ReadFile;

import java.util.*;

class Coordinates {

  public final int x;
  public final int y;
  public final int currentHeight;

  public Coordinates(int x, int y, int currentHeight) {
    this.x = x;
    this.y = y;
    this.currentHeight = currentHeight;
  }
}

public class Day10 {

  private final List<List<Integer>> fileInput;

  public Day10() {
    ReadFile readFile = new ReadFile();
    fileInput = Arrays.stream(readFile.main("day10/input.txt").split("\n"))
        .map(e -> Arrays.stream(e.split("")).map(Integer::parseInt).toList())
        .toList();
  }

  private List<Coordinates> findTrailHeads() {
    ArrayList<Coordinates> trailHeads = new ArrayList<>();
    for (int y = 0; y < fileInput.size(); y++) {
      for (int x = 0; x < fileInput.get(y).size(); x++) {
        if (fileInput.get(y).get(x) == 0) {
          trailHeads.add(new Coordinates(x, y, 0));
        }
      }
    }

    return trailHeads;
  }

  private Integer calculateTrailScore(Coordinates trailHead, boolean distinct) {
    ArrayList<Coordinates> currentPositions = new ArrayList<>();
    currentPositions.add(trailHead);
    ArrayList<String> completedTrails = new ArrayList<>();

    do {
      ArrayList<Coordinates> availableMoves = new ArrayList<>();
        for (Coordinates currentPosition : currentPositions) {
          int x = currentPosition.x;
          int y = currentPosition.y;
          if (fileInput.get(y).get(x) == 9) {
            completedTrails.add(x+ ", " + y);
            continue;
          }
          if (x + 1 < fileInput.get(y).size() && fileInput.get(y).get(x + 1) == currentPosition.currentHeight +1) {
            availableMoves.add(new Coordinates(x + 1, y, currentPosition.currentHeight + 1));
          }
          if (y + 1 < fileInput.size() && fileInput.get(y + 1).get(x)  == currentPosition.currentHeight +1) {
            availableMoves.add(new Coordinates(x, y + 1, currentPosition.currentHeight + 1));
          }
          if (x - 1 >= 0 && fileInput.get(y).get(x - 1) == currentPosition.currentHeight +1) {
            availableMoves.add(new Coordinates(x - 1, y, currentPosition.currentHeight + 1));
          }
          if (y - 1 >= 0 && fileInput.get(y - 1).get(x) == currentPosition.currentHeight +1) {
            availableMoves.add(new Coordinates(x, y - 1, currentPosition.currentHeight + 1));
          }
        }
        currentPositions = availableMoves;
    } while (currentPositions.size() > 0);
    if(distinct) {
      return new LinkedHashSet<String>(completedTrails).size();
    } else {
        return completedTrails.size();
    }
  }

  private List<Integer> calculateTailScores(List<Coordinates> trailHeads, boolean distinct) {
    ArrayList<Integer> trailScores = new ArrayList<>();
    for (Coordinates trailHead : trailHeads) {
      trailScores.add(calculateTrailScore(trailHead, distinct));
    }

    return trailScores;
  }

  private void part1() {
    List<Coordinates> trailHeads = findTrailHeads();
    List<Integer> trailScores = calculateTailScores(trailHeads, true);
    Integer summedScore = 0;
    for (Integer trailScore : trailScores) {
      summedScore += trailScore;
    }
    System.out.printf("[Part 1] Trail heads summed score: %s\n", summedScore);
  }

  private void part2() {
    List<Coordinates> trailHeads = findTrailHeads();
    List<Integer> trailScores = calculateTailScores(trailHeads, false);
    Integer summedScore = 0;
    for (Integer trailScore : trailScores) {
      summedScore += trailScore;
    }
    System.out.printf("[Part 2] Trail heads summed score: %s\n", summedScore);
  }

  public void run() {
    System.out.println("------ Day 10 ------");
    part1();
    part2();
  }
}
