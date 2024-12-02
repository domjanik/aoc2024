package Java.utils;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {
  public String main(String fileName) {
    StringBuilder data = new StringBuilder();
    try {
      File myObj = new File("/Users/dominikjanik/projects/priv/aoc2024/Java/" + fileName);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data.append(myReader.nextLine()).append("\n");

      }
      myReader.close();

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    return data.toString();
  }
}
