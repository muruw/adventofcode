package _2021.day2_dive;

import _2021.util.Utils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Dive {
  final List<String> VERTICAL_DIRECTIONS = List.of("down", "up");
  final List<String> HORIZONTAL_DIRECTIONS = List.of("forward", "backward");
  final List<String> NEGATIVE_VALUES = List.of("up", "backward");

  public static void main(String[] args) throws IOException, URISyntaxException {
    Dive dive = new Dive();
    List<String> fileRows = Utils.readLinesFromResourceFile("/2021/dive", Dive.class);
    Map<String, Integer> directionAndDepth = dive.readDepthAndHorizontalValue(fileRows);
    Map<String, Integer> updatedDirectionAndDepth = dive.readDepthAndHorizontalValueUpdated(fileRows);
    // Task 1
    System.out.println(directionAndDepth.get("horizontalValue") * directionAndDepth.get("depth"));
    // Task 2
    System.out.println(updatedDirectionAndDepth.get("horizontalValue") * updatedDirectionAndDepth.get("depth"));
  }

  // task 1
  private Map<String, Integer> readDepthAndHorizontalValue(List<String> directionsAndDepths) throws IOException, URISyntaxException {
    int horizontalPos = 0;
    int depth = 0;
    for (String row : directionsAndDepths) {
      String[] directionAndValue = row.split(" ");
      String direction = directionAndValue[0];
      int value = Integer.parseInt(directionAndValue[1]);
      value = NEGATIVE_VALUES.contains(direction) ? -1*value : value;
      if (VERTICAL_DIRECTIONS.contains(direction)) horizontalPos += value;
      else if (HORIZONTAL_DIRECTIONS.contains(direction)) depth += value;
    }

    return Map.of("horizontalValue", horizontalPos, "depth", depth);
  }

  // Task 2
  private Map<String, Integer> readDepthAndHorizontalValueUpdated(List<String> directionsAndDepths) throws IOException, URISyntaxException {
    int horizontalPos = 0;
    int depth = 0;
    int aim = 0;
    for (String row : directionsAndDepths) {
      String[] directionAndValue = row.split(" ");
      String direction = directionAndValue[0];
      int value = Integer.parseInt(directionAndValue[1]);
      value = NEGATIVE_VALUES.contains(direction) ? -1 * value : value;
      if (VERTICAL_DIRECTIONS.contains(direction)) aim += value;
      else if (HORIZONTAL_DIRECTIONS.contains(direction)) {
        horizontalPos += value;
        depth += aim * value;
      }
    }

    return Map.of("horizontalValue", horizontalPos, "depth", depth);
  }
}
