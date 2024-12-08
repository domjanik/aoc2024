package Java.day8;

import Java.utils.ReadFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Coordinates {

  public final int x;
  public final int y;

  public Coordinates(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

class AntenaPositions {

  private final String frequency;
  private final ArrayList<Coordinates> positionList;

  private final List<List<String>> map;

  public AntenaPositions(String frequency, Coordinates position, List<List<String>> map) {
    this.frequency = frequency;
    this.positionList = new ArrayList<>();
    this.positionList.add(position);
    this.map = map;
  }

  public void addPosition(Coordinates position) {
    this.positionList.add(position);
  }

  public String getFrequency() {
    return this.frequency;
  }

  private Coordinates findPreviousAntinode(Coordinates node1, Coordinates node2, Integer diffX, Integer diffY) {
    return new Coordinates(
        node1.x > node2.x ? node1.x + diffX : node1.x - diffX,
        node1.y - diffY
    );
  }

  private Coordinates findNextAntinode(Coordinates node1, Coordinates node2, Integer diffX, Integer diffY) {
    return new Coordinates(
        node1.x > node2.x ? node2.x - diffX  : node2.x + diffX,
        node2.y + diffY
    );
  }

  private boolean antinodeValid(Coordinates antinode, List<Coordinates> antiNodes) {
    return antinode.y < map.size() &&
           antinode.x < map.get(0).size() &&
           antinode.x >= 0 && antinode.y >= 0 &&
           !antiNodes.contains(antinode);
  }

  public List<Coordinates> findAntiNodes(boolean singleAntinode) {
    ArrayList<Coordinates> antiNodes = new ArrayList<>();
    for (int i = 0; i < this.positionList.size(); i++) {
      Coordinates node1 = this.positionList.get(i);
      for (int j = this.positionList.size() - 1; j > i; j--) {
        Coordinates node2 = this.positionList.get(j);
        if(!singleAntinode) {
          antiNodes.add(node1);
          antiNodes.add(node2);
        }

        int xDiff = Math.abs(node1.x - node2.x);
        int yDiff = Math.abs(node1.y - node2.y);

        Coordinates tempNode1 = node1;
        Coordinates tempNode2 = node2;

        do {
          Coordinates antiNode = findPreviousAntinode(tempNode1, tempNode2, xDiff, yDiff);

          if (antinodeValid(antiNode, antiNodes)) {
            antiNodes.add(antiNode);
          }
          tempNode2 = tempNode1;
          tempNode1 = antiNode;
        } while (!singleAntinode && tempNode2.x > 0 && tempNode2.y > 0 && tempNode2.y < map.size() && tempNode2.x < map.get(0).size());

        tempNode1 = node1;
        tempNode2 = node2;
        do {
          Coordinates antiNode = findNextAntinode(tempNode1, tempNode2, xDiff, yDiff);

          if (antinodeValid(antiNode, antiNodes)) {
            antiNodes.add(antiNode);
          }
          tempNode1 = tempNode2;
          tempNode2 = antiNode;
        } while (!singleAntinode && tempNode2.x > 0 && tempNode2.y > 0 && tempNode2.y < map.size() && tempNode2.x < map.get(0).size());
      }
    }
    return antiNodes;
  }
}

public class Day8 {
  private final List<List<String>> fileInput;

  public Day8() {
    ReadFile readFile = new ReadFile();
    fileInput = Arrays.stream(readFile.main("day8/input.txt").split("\n"))
        .map(e -> Arrays.asList(e.split("")))
        .toList();
  }

  private void part1() {
    ArrayList<AntenaPositions> antenaPositions = new ArrayList<>();
    for(int y = 0; y < fileInput.size(); y++) {
        List<String> line = fileInput.get(y);
    for(int x = 0; x < line.size(); x++) {
        String frequency = line.get(x);
        if(frequency.equals(".") || frequency.equals("#")) {
            continue;
        }
        Coordinates position = new Coordinates(x, y);
        AntenaPositions antenaPosition = antenaPositions.stream()
            .filter(a -> a.getFrequency().equals(frequency))
            .findFirst()
            .orElse(null);

        if (antenaPosition == null) {
          antenaPosition = new AntenaPositions(frequency, position, fileInput);
          antenaPositions.add(antenaPosition);
        } else {
          antenaPosition.addPosition(position);
        }
      }
    }

    ArrayList<String> antiNodes = new ArrayList<>();

    for (AntenaPositions antenaPosition : antenaPositions) {
      List<Coordinates> frequencyAntiNodes = antenaPosition.findAntiNodes(true);
        for (Coordinates antiNode : frequencyAntiNodes) {
          if(!antiNodes.contains(String.format("%d,%d", antiNode.x, antiNode.y))) {
            antiNodes.add(String.format("%d,%d", antiNode.x, antiNode.y));
          }
        }
    }

    System.out.printf("[Part 1]: Found %d antinodes \n", antiNodes.size());
  }

  private void part2() {
    ArrayList<AntenaPositions> antenaPositions = new ArrayList<>();
    for(int y = 0; y < fileInput.size(); y++) {
      List<String> line = fileInput.get(y);
      for(int x = 0; x < line.size(); x++) {
        String frequency = line.get(x);
        if(frequency.equals(".") || frequency.equals("#")) {
          continue;
        }
        Coordinates position = new Coordinates(x, y);
        AntenaPositions antenaPosition = antenaPositions.stream()
            .filter(a -> a.getFrequency().equals(frequency))
            .findFirst()
            .orElse(null);

        if (antenaPosition == null) {
          antenaPosition = new AntenaPositions(frequency, position, fileInput);
          antenaPositions.add(antenaPosition);
        } else {
          antenaPosition.addPosition(position);
        }
      }
    }

    ArrayList<String> antiNodes = new ArrayList<>();

    for (AntenaPositions antenaPosition : antenaPositions) {
      List<Coordinates> frequencyAntiNodes = antenaPosition.findAntiNodes(false);
      for (Coordinates antiNode : frequencyAntiNodes) {
        if(!antiNodes.contains(String.format("%d,%d", antiNode.x, antiNode.y))) {
          antiNodes.add(String.format("%d,%d", antiNode.x, antiNode.y));
        }
      }
    }

    System.out.printf("[Part 2]: Found %d antinodes \n", antiNodes.size());
  }

  public void run() {
    System.out.println("------ Day 8 ------");
    part1();
    part2();
  }
}
