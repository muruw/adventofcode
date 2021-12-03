package _2021.day1_sonar_sweep;

import _2021.util.Utils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class SonarSweep {
  public static void main(String[] args) throws URISyntaxException, IOException {
    SonarSweep sonarSweep = new SonarSweep();
    sonarSweep.sonarSweep("/2021/sonarsweep");
    sonarSweep.sonarSweepThreeMeasurementSlidingWindow("/2021/sonarsweep");
  }

  public void sonarSweep(String resourceFileName) throws URISyntaxException, IOException {
    List<String> seaFloorMeasurements = Utils.readLinesFromResourceFile(resourceFileName, SonarSweep.class);
    System.out.println(seaFloorMeasurements.get(0) + " (N/A - no previous sum)");
    int total = 0;
    for (int i = 1; i < seaFloorMeasurements.size(); i++) {
      int previous = Integer.parseInt(seaFloorMeasurements.get(i - 1));
      int current = Integer.parseInt(seaFloorMeasurements.get(i));
      boolean hasIncreased = current > previous;
      String increasedOrDecreased = hasIncreased ? " (increased)" : " (decreased)";
      System.out.println(current + increasedOrDecreased);
      if (hasIncreased) total++;
    }
    System.out.println("How many measurements are larger than the previous measurement? " + total);
  }

  public void sonarSweepThreeMeasurementSlidingWindow(String resourceFileName) throws URISyntaxException, IOException {
    List<String> seaFloorMeasurements = Utils.readLinesFromResourceFile(resourceFileName, SonarSweep.class);

    int firstElement = Integer.parseInt(seaFloorMeasurements.get(0));
    int secondElement = Integer.parseInt(seaFloorMeasurements.get(1));
    int thirdElement = Integer.parseInt(seaFloorMeasurements.get(2));
    System.out.println(firstElement+secondElement+thirdElement + " (N/A - no previous sum)");

    int total = 0;
    int previousSum = 0;
    for (int i = 3; i < seaFloorMeasurements.size(); i++) {
      int first = Integer.parseInt(seaFloorMeasurements.get(i - 2));
      int second = Integer.parseInt(seaFloorMeasurements.get(i - 1));
      int third = Integer.parseInt(seaFloorMeasurements.get(i));
      int sum = first + second + third;
      boolean hasIncreased = sum > previousSum;
      String increasedOrDecreased = hasIncreased ? " (increased)" : " (decreased)";
      System.out.println(sum + increasedOrDecreased);
      if (hasIncreased) total++;
      previousSum = sum;
    }
    System.out.println("How many sums are larger than the previous sum? " + total);
  }
}
