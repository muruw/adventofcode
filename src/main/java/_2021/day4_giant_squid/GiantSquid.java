package _2021.day4_giant_squid;

import _2021.util.Utils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GiantSquid {

  public static void main(String[] args) throws URISyntaxException, IOException {
    GiantSquid gs = new GiantSquid();
    List<String> rows = Utils.readLinesFromResourceFile("/2021/giantsquid", GiantSquid.class);
    gs.findBingoWinner(rows);
  }

  private void findBingoWinner(List<String> rows) {
    List<Integer> bingoNumbers = Arrays.stream(rows.get(0).split(",")).map(Integer::valueOf).collect(Collectors.toList());

    // Delete all empty rows
    rows.removeAll(Arrays.asList("", null));
    // Create bingo tables
    // Increment by 5 because bingo tables are 5x5 in size
    List<List<List<Integer>>> bingoTables = new ArrayList<>();
    for (int i = 5; i < rows.size(); i += 5) {
      List<String> tableSublist = rows.subList(i - 4, i + 1);
      bingoTables.add(createBingoTable(tableSublist));
    }


    // TASK 1
    for (int k = 0; k < bingoNumbers.size(); k++) {
      List<Integer> drawnBingoNumbers = bingoNumbers.subList(0, k + 1);
      for (List<List<Integer>> bingoTable : bingoTables) {
        if (hasBingoBoardWon(drawnBingoNumbers, bingoTable)) return;
      }
    }
  }

  private List<List<Integer>> createBingoTable(List<String> rows) {
    List<List<Integer>> bingoTable = new ArrayList<>();
    for (String row : rows) {
      List<Integer> bingoRow = Arrays.stream(row.trim().split("\\s+")).map(Integer::valueOf).collect(Collectors.toList());
      bingoTable.add(bingoRow);
    }
    return bingoTable;
  }

  private boolean hasBingoBoardWon(List<Integer> drawnBingoNumbers, List<List<Integer>> bingoTable) {
    for (int i = 0; i < 5; i++) {
      // check vertical rows
      List<Integer> verticalMatch = new ArrayList<>();
      List<Integer> horizontalMatch = new ArrayList<>();
      for (int j = 0; j < 5; j++) {
        Integer tableNumber = bingoTable.get(j).get(i);
        if (drawnBingoNumbers.contains(tableNumber)) verticalMatch.add(tableNumber);
      }
      // check horizontal rows
      for (int j = 0; j < 5; j++) {
        Integer tableNumber = bingoTable.get(i).get(j);
        if (drawnBingoNumbers.contains(tableNumber)) horizontalMatch.add(tableNumber);
      }
      if (verticalMatch.size() == 5 || horizontalMatch.size() == 5) {
        int lastDrawnNumber = drawnBingoNumbers.get(drawnBingoNumbers.size() - 1);
        System.out.println("5 same numbers found!");
        System.out.println("Vertical: " + verticalMatch);
        System.out.println("Horizontal: " + horizontalMatch);
        System.out.println("Last drawn bingo number: " + lastDrawnNumber);
        System.out.println("Task 1 answer: " + calculateSumOfUnusedNumbers(bingoTable, drawnBingoNumbers) * lastDrawnNumber);
        return true;
      }
    }
    return false;
  }

  private int calculateSumOfUnusedNumbers(List<List<Integer>> bingoTable, List<Integer> drawnBingoNumbers) {
    int sum = 0;
    for (List<Integer> row : bingoTable) {
      for (Integer number : row) {
        if (!drawnBingoNumbers.contains(number)) sum += number;
      }
    }
    return sum;
  }
}
