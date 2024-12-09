package Java.day9;

import Java.utils.ReadFile;

import java.util.*;

class FileMap {

  public final List<String> map;
  public final List<String> fileList;

  public FileMap(List<String> map, List<String> fileList) {
    this.map = map;
    this.fileList = fileList;
  }
}

public class Day9 {

  private final String fileInput;

  public Day9() {
    ReadFile readFile = new ReadFile();
    fileInput = readFile.main("day9/input.txt").trim();
  }

  private FileMap generateFileIdMap(String file) {
    ArrayList<String> map = new ArrayList<>();
    List<String> fileArray = Arrays.stream(file.split("")).toList();
    ArrayList<String> files = new ArrayList<>();
    int fileId = 0;
    for (int i = 0; i < fileArray.size(); i++) {
      int size = Integer.parseInt(fileArray.get(i));
      if (i % 2 == 0) {
        String fileIdString = String.valueOf(fileId);
        files.add(fileIdString);
        for (int j = 0; j < size; j++) {
          map.add(fileIdString);
        }
        fileId++;
      } else {
        for (int j = 0; j < size; j++) {
          map.add(".");
        }
      }
    }
    return new FileMap(map, files);
  }

  private Integer getLastNotEmptyIndex(List<String> map) {
    for (int i = map.size() - 1; i >= 0; i--) {
      if (!map.get(i).equals(".")) {
        return i;
      }
    }
    return -1;
  }

  private List<String> fragmentateFiles(List<String> map) {
    ArrayList<String> newMap = new ArrayList<>();

    for (int i = 0; i < map.size(); i++) {
      String file = map.get(i);
      if (file.equals(".")) {
        int lastFileIndex = getLastNotEmptyIndex(map);
        if (lastFileIndex == -1 || lastFileIndex < i) {
          break;
        }
        newMap.add(map.get(lastFileIndex));
        map.remove(lastFileIndex);
      } else if (!file.equals("")) {
        newMap.add(file);
      }
    }

    return newMap;
  }

  private List<String> reorderFiles(List<String> map, List<String> fileList) {
    ArrayList<String> newMap = new ArrayList<>(map);
    for (int i = fileList.size() - 1; i>=0; i--) {
      String file = fileList.get(i);
      int firstAppearance = newMap.indexOf(file);
      int occurances = newMap.stream().filter(e -> e.equals(file)).toList().size();
      List<String> dotList = Arrays.stream(".".repeat(occurances).split("")).toList();
      int possibleIndex = Collections.indexOfSubList(newMap, dotList);
      if(possibleIndex >= firstAppearance) {
        continue;
      }
      if (possibleIndex != -1) {
        for (int j = 0; j < occurances; j++) {
          newMap.set(possibleIndex + j, file);
          newMap.set(firstAppearance + j, ".");
        }
      }
    }

    return newMap;
  }

  private Long calculateControlSum(List<String> map) {
    long controlSum = 0L;
    for (int i = 0; i < map.size(); i++) {
      String block = map.get(i);
      if(!block.equals(".")) {
        controlSum += Long.parseLong(block) * i;
      }
    }
    return controlSum;
  }

  private void part1() {
    List<String> map = generateFileIdMap(fileInput).map;

    List<String> reasignedMap = fragmentateFiles(map);

    long controlSum = calculateControlSum(reasignedMap);

    System.out.printf("[Part 1]: %d \n", controlSum);
  }

  private void part2() {
    FileMap map = generateFileIdMap(fileInput);
    List<String> reasignedMap = reorderFiles(map.map, map.fileList);

    long controlSum = calculateControlSum(reasignedMap);

    System.out.printf("[Part 2]: %d \n", controlSum);
  }

  public void run() {
    System.out.println("------ Day 9 ------");
    part1();
    part2();
  }
}
