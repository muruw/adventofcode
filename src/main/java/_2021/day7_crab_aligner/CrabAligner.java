package _2021.day7_crab_aligner;

import _2021.util.Utils;
import java.io.IOException;
import java.net.URISyntaxException;

public class CrabAligner {

  public static void main(String[] args) throws URISyntaxException, IOException {
    int[] crabPositions = generateCrabPositions();
    System.out.println(part2(crabPositions));

    System.out.println(calculateCrabFuelUsage(0));
  }

  private static int part1(int[] crabPositions) {
    int smallestFuel = Integer.MAX_VALUE;
    for (int pos: crabPositions) {
      int fuelUsageWithPos = 0;
      for (int curPositions : crabPositions) {
        fuelUsageWithPos += (Math.abs(pos - curPositions));
      }
      if (fuelUsageWithPos < smallestFuel) smallestFuel = fuelUsageWithPos;
    }
    return smallestFuel;
  }

  private static int part2(int[] crabPositions) {
    int smallestFuel = Integer.MAX_VALUE;
    for (int i = 1; i < crabPositions.length; i++) {
      int fuelUsageWithPos = 0;
      for (int curPositions : crabPositions) {
        fuelUsageWithPos += (calculateCrabFuelUsage(Math.abs(i - curPositions)));
      }
      if (fuelUsageWithPos < smallestFuel) smallestFuel = fuelUsageWithPos;
    }
    return smallestFuel;
  }

  private static int calculateCrabFuelUsage(int x) {
    return ((x + 1) * x) / 2;
  }

  private static int[] generateCrabPositions() throws URISyntaxException, IOException {
    String[] positions = Utils.readLinesFromResourceFile("/2021/crabpositions", CrabAligner.class).get(0).split(",");
    int[] positionsAsInteger = new int[positions.length];
    for (int i = 0; i < positions.length; i++) {
      positionsAsInteger[i] = Integer.parseInt(positions[i]);
    }
    return positionsAsInteger;
  }

}
