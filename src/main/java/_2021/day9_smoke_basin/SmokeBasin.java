package _2021.day9_smoke_basin;

import _2021.util.Utils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

public class SmokeBasin {
  private record Coord(int x, int y) {}

  public static void main(String[] args) throws URISyntaxException, IOException {
    List<String> rows = Utils.readLinesFromResourceFile("/2021/smokebasin", SmokeBasin.class);
    Integer[][] rowsAsIntegers = convertListToNestedArray(rows);
    List<Coord> coords = findLowPoints(rowsAsIntegers);
    System.out.println("Part 1: " + (heightMapValue(coords, rowsAsIntegers) + coords.size()));
    System.out.println("Part 2: " + findBasins(rowsAsIntegers, coords));
  }

  private static int findBasins(Integer[][] field, List<Coord> coordinates) {
    List<Integer> basinSizes = new ArrayList<>();
    for (Coord coordinate : coordinates) {
      basinSizes.add(findBasinSize(field, coordinate));
    }
    basinSizes.sort(Collections.reverseOrder());
    return basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
  }

  private static int findBasinSize(Integer[][] field, Coord lowestPoint) {
    int maxx = field.length;
    int maxy = field[0].length;
    boolean[][] seenElements = new boolean[maxx][maxy];
    Deque<Coord> pending = new ArrayDeque<>();
    pending.addFirst(lowestPoint);
    int basinSize = 0;

    while (!pending.isEmpty()) {
      Coord point = pending.removeFirst();
      int x = point.x;
      int y = point.y;
      if (seenElements[x][y] || field[x][y] == 9) {
        continue;
      }
      basinSize++;
      seenElements[x][y] = true;
      if (x > 0 && !seenElements[x - 1][y] && field[x - 1][y] != 9) pending.addLast(new Coord(x - 1, y));
      if (x < maxx - 1 && !seenElements[x + 1][y] && field[x + 1][y] != 9) pending.addLast(new Coord(x + 1, y));
      if (y > 0 && !seenElements[x][y - 1] && field[x][y - 1] != 9) pending.addLast(new Coord(x, y - 1));
      if (y < maxy - 1 && !seenElements[x][y + 1] && field[x][y + 1] != 9) pending.addLast(new Coord(x, y + 1));
    }
    return basinSize;
  }

  private static Integer[][] convertListToNestedArray(List<String> rows) {
    Integer[][] field = new Integer[rows.size()][rows.get(0).length()];

    for (int i = 0; i < rows.size(); i++) {
      String[] row = rows.get(i).split("");
      Integer[] rowWithIntValues = Stream.of(row).map(Integer::parseInt).toList().toArray(Integer[]::new);
      field[i] = rowWithIntValues;
    }
    return field;
  }

  private static int heightMapValue(List<Coord> coordinates, Integer[][] field) {
    int sum = 0;
    for (Coord coord : coordinates) {
      sum = sum + field[coord.x][coord.y];
    }
    return sum;
  }

  private static List<Coord> findLowPoints(Integer[][] field) {
    List<Coord> lowPointCoordinates = new ArrayList<>();
    int maxx = field.length;
    int maxy = field[0].length;
    for (int i = 0; i < maxx; i++) {
      for (int j = 0; j < maxy; j++) {
        if (isLowPoint(field, i, j)) {
          lowPointCoordinates.add(new Coord(i, j));
        }
      }
    }
    return lowPointCoordinates;
  }

  private static boolean isLowPoint(Integer[][] field, int x, int y) {
    Integer point = field[x][y];
    if (x > 0 && point >= field[x - 1][y]) return false;
    else if (x < field.length - 1 && point >= field[x + 1][y]) return false;
    else if (y > 0 && point >= field[x][y - 1]) return false;
    else if (y < field[0].length - 1 && point >= field[x][y + 1]) return false;
    return true;
  }
}
