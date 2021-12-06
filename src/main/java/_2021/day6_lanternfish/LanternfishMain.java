package _2021.day6_lanternfish;

import _2021.util.Utils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class LanternfishMain {

  public static void main(String[] args) throws URISyntaxException, IOException {
    long amountOfDaysToSimulate = 256;

    // 1st index represent the amount of fishes that have 0 days til reproducing and so on
    long[] amountOfFishesInEachReproducingDay = generateLanternfishes();
    System.out.println(Arrays.toString(amountOfFishesInEachReproducingDay));
    for (int i = 0; i < amountOfDaysToSimulate; i++) {
      long[] currentDayFishStatistics = {0, 0, 0, 0, 0, 0, 0, 0, 0};
      for (int j = 0; j < amountOfFishesInEachReproducingDay.length; j++) {
        long amountOfFishes = amountOfFishesInEachReproducingDay[j];
        if (j == 0) {
          currentDayFishStatistics[6] += amountOfFishes;
          currentDayFishStatistics[8] = amountOfFishes;
        } else {
          currentDayFishStatistics[j - 1] += amountOfFishes;
        }
        amountOfFishesInEachReproducingDay[j] = 0;
      }
      amountOfFishesInEachReproducingDay = currentDayFishStatistics;
    }
    System.out.println(Arrays.toString(amountOfFishesInEachReproducingDay));
    System.out.println(Arrays.stream(amountOfFishesInEachReproducingDay).sum());
  }

  private static long[] generateLanternfishes() throws URISyntaxException, IOException {
    long[] amountOfFishesInEachReproducingDay = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    String[] fishInitialStates = Utils.readLinesFromResourceFile("/2021/lanternfish", LanternfishMain.class).get(0).split(",");
    for (String fishInitialState : fishInitialStates) {
      amountOfFishesInEachReproducingDay[Integer.parseInt(fishInitialState)] += 1;
    }
    return amountOfFishesInEachReproducingDay;
  }
}
