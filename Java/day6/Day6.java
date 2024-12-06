package Java.day6;

import Java.utils.ReadFile;

import java.util.*;

enum GuardDirections {
  UP, DOWN, LEFT, RIGHT
}

class Guard {

  private Integer positionX;
  private Integer positionY;

  private GuardDirections direction;

  private final List<List<String>> map;

  public Guard(Integer positionY, Integer positionX, GuardDirections direction, List<List<String>> map) {
    this.positionX = positionX;
    this.positionY = positionY;
    this.direction = direction;
    this.map = map;
  }

  private void turnAround() {
    if (direction == GuardDirections.UP) {
      direction = GuardDirections.RIGHT;
    } else if (direction == GuardDirections.DOWN) {
      direction = GuardDirections.LEFT;
    } else if (direction == GuardDirections.LEFT) {
      direction = GuardDirections.UP;
    } else if (direction == GuardDirections.RIGHT) {
      direction = GuardDirections.DOWN;
    }
  }

  public Integer[] getPosition() {
    return new Integer[]{positionY, positionX, direction.ordinal()};
  }

  public void move() {
    if (direction == GuardDirections.UP) {
      if (positionY > 0 && map.get(positionY - 1).get(positionX).equals("#")) {
        turnAround();
        move();
      } else {
        map.get(positionY).set(positionX, "X");
        positionY--;
      }
    } else if (direction == GuardDirections.DOWN) {
      if (positionY < map.size() - 1 && map.get(positionY + 1).get(positionX).equals("#")) {
        turnAround();
        move();
      } else {
        map.get(positionY).set(positionX, "X");
        positionY++;
      }
    } else if (direction == GuardDirections.LEFT) {
      if (positionX > 0 && map.get(positionY).get(positionX - 1).equals("#")) {
        turnAround();
        move();
      } else {
        map.get(positionY).set(positionX, "X");
        positionX--;
      }
    } else if (direction == GuardDirections.RIGHT) {
      if (positionX < map.get(0).size() - 1 && map.get(positionY).get(positionX + 1).equals("#")) {
        turnAround();
        move();
      } else {
        map.get(positionY).set(positionX, "X");
        positionX++;
      }
    }
  }
}

public class Day6 {

  private final List<String> PossibleGuardIcons = List.of("^", "v", "<", ">");
  private final List<List<String>> fileInput;

  public Day6() {
    ReadFile readFile = new ReadFile();
    fileInput = Arrays.stream(readFile.main("day6/input.txt").split("\n"))
        .map(e -> Arrays.asList(e.split("")))
        .toList();
  }

  private int[] findGuardPosition() {
    for (int i = 0; i < fileInput.size(); i++) {
      List<String> line = fileInput.get(i);
      for (int j = 0; j < line.size(); j++) {
        if (PossibleGuardIcons.contains(line.get(j))) {
          return new int[]{i, j};
        }
      }
    }
    return new int[2];
  }

  private GuardDirections getGuardDirection(int x, int y) {
    String guard = fileInput.get(y).get(x);
    if (Objects.equals(guard, "^")) {
      return GuardDirections.UP;
    } else if (Objects.equals(guard, "v")) {
      return GuardDirections.DOWN;
    } else if (Objects.equals(guard, "<")) {
      return GuardDirections.LEFT;
    } else {
      return GuardDirections.RIGHT;
    }
  }

  private Guard initializeGuard(List<List<String>> map) {
    int[] guardPosition = findGuardPosition();
    GuardDirections guardDirection = getGuardDirection(guardPosition[1], guardPosition[0]);

    return new Guard(guardPosition[0], guardPosition[1], guardDirection, map);
  }

  private List<String> part1() {
    List<List<String>> mapCopy = new ArrayList<>();
    for (List<String> strings : fileInput) {
      mapCopy.add(new ArrayList<>(strings));
    }
    Guard guard = initializeGuard(mapCopy);
    ArrayList<String> positions = new ArrayList<>();

    boolean guardOnMap = true;
    while (guardOnMap) {
      guard.move();
      if (guard.getPosition()[0] == mapCopy.size() ||
          guard.getPosition()[0] < 0 ||
          guard.getPosition()[1] < 0 ||
          guard.getPosition()[1] == mapCopy.get(guard.getPosition()[0]).size()) {
        guardOnMap = false;
      } else {
        positions.add(String.format("%d,%d", guard.getPosition()[0], guard.getPosition()[1]));
      }
    }
      System.out.printf(
        "[Part 1] Guard has made %d steps\n",
        mapCopy.stream().flatMap(List::stream).toList().stream().filter(e -> e.equals("X")).count()
    );
    return new HashSet<String>(positions).stream().toList();
  }

  private void part2(List<String> positions) {
    Integer possibleObstacles = 0;
    int[] initialGuardPosition = findGuardPosition();
    GuardDirections initialGuardDirection = getGuardDirection(initialGuardPosition[1], initialGuardPosition[0]);

    for (int i = 0 ; i < positions.size(); i++) {
      List<List<String>> mapCopy = new ArrayList<>();
      for (List<String> strings : fileInput) {
        mapCopy.add(new ArrayList<>(strings));
      }
      List<Integer> obstaclePosition = Arrays.stream(positions.get(i).split(",")).map(Integer::parseInt).toList();

      mapCopy.get(obstaclePosition.get(0)).set(obstaclePosition.get(1), "#");

      Guard guard = new Guard(initialGuardPosition[0], initialGuardPosition[1], initialGuardDirection, mapCopy);
      ArrayList<String> positionsWithObstacle = new ArrayList<>();

      boolean guardOnMap = true;
      boolean guardStuck = false;
      while (guardOnMap && !guardStuck) {
        guard.move();
        String newPosition = String.format("%d,%d,%d", guard.getPosition()[0], guard.getPosition()[1], guard.getPosition()[2]);
        if (positionsWithObstacle.contains(newPosition)) {
          guardStuck = true;
          possibleObstacles++;
        } else {
          positionsWithObstacle.add(newPosition);
          if (guard.getPosition()[0] == mapCopy.size() ||
              guard.getPosition()[0] < 0 ||
              guard.getPosition()[1] < 0 ||
              guard.getPosition()[1] == mapCopy.get(guard.getPosition()[0]).size()) {
            guardOnMap = false;
          }
        }
      }
    }
    System.out.printf("[Part 2] There are %d obstacles\n", possibleObstacles);
  }

  public void run() {
    System.out.println("------ Day 6 ------");
    List<String> positions = part1(); // 4580
    part2(positions); // 1480
  }
}
