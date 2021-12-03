package _2021.day3_binary_diagnostics;

import static _2021.day3_binary_diagnostics.DiagnosticsType.CO2;
import static _2021.day3_binary_diagnostics.DiagnosticsType.EPSILON;
import static _2021.day3_binary_diagnostics.DiagnosticsType.GAMMA;
import static _2021.day3_binary_diagnostics.DiagnosticsType.OXYGEN;

import _2021.util.Utils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryDiagnostics {

  public static void main(String[] args) throws URISyntaxException, IOException {
    BinaryDiagnostics bd = new BinaryDiagnostics();
    List<String> fileRows = Utils.readLinesFromResourceFile("/2021/day3-diagnostics", BinaryDiagnostics.class);
    Map<DiagnosticsType, String> gammaAndEpsilonRates = bd.calculateGammaAndEpsilonRates(fileRows);
    int gammaAsInteger = Integer.parseInt(gammaAndEpsilonRates.get(GAMMA), 2);
    int epsilonAsInteger = Integer.parseInt(gammaAndEpsilonRates.get(EPSILON), 2);

    System.out.println(GAMMA + ": " + gammaAsInteger);
    System.out.println(EPSILON + ": " + epsilonAsInteger);
    System.out.println(gammaAsInteger * epsilonAsInteger);
    bd.calculateOxygenGeneratorAndCO2ScrubberRatings(fileRows, OXYGEN);
    bd.calculateOxygenGeneratorAndCO2ScrubberRatings(fileRows, CO2);
  }

  private Map<DiagnosticsType, String> calculateGammaAndEpsilonRates(List<String> fileRows) {
    Map<DiagnosticsType, String> gammaAndEpsilonRates = new HashMap<>();
    Map<Integer, String> mostCommonBitsInVerticalRows = new HashMap<>();

    for (int i = 0; i < fileRows.get(i).length(); i++) {
      Map<String, Integer> amountOfBitsInEachVerticalRow = getAmountOfBitsInEachVerticalRow(fileRows, i);
      int zeros = amountOfBitsInEachVerticalRow.get("zeros");
      int ones = amountOfBitsInEachVerticalRow.get("ones");
      mostCommonBitsInVerticalRows.put(i, zeros > ones ? "0" : "1");
    }

    gammaAndEpsilonRates.put(GAMMA, "");
    gammaAndEpsilonRates.put(EPSILON, "");
    for (int i = 0; i < mostCommonBitsInVerticalRows.size(); i++) {
      String mostCommonBit = mostCommonBitsInVerticalRows.get(i);
      String leastCommonBit = Integer.toString(Math.abs(Integer.parseInt(mostCommonBitsInVerticalRows.get(i)) - 1));
      gammaAndEpsilonRates.put(GAMMA, gammaAndEpsilonRates.get(GAMMA) + mostCommonBit);
      gammaAndEpsilonRates.put(EPSILON, gammaAndEpsilonRates.get(EPSILON) + leastCommonBit);
    }
    return gammaAndEpsilonRates;
  }

  private String calculateOxygenGeneratorAndCO2ScrubberRatings(List<String> fileRows, DiagnosticsType type) {
    for (int i = 0; i < 12; i++) {
      // edge case
      if (fileRows.size() == 1) {
        System.out.println(type + ": " + Integer.parseInt(fileRows.get(0), 2));
        return fileRows.get(0);
      }
      List<String> bitsWithZero = new ArrayList<>();
      List<String> bitsWithOne = new ArrayList<>();
      // All bits in the input file are 12 characters long
      for (String bits: fileRows) {
        int bit = Character.getNumericValue(bits.charAt(i));
        if (bit == 0) bitsWithZero.add(bits);
        else if (bit == 1) bitsWithOne.add(bits);
      }
      fileRows = type == OXYGEN ?
          bitsWithZero.size() < bitsWithOne.size() ? bitsWithOne : bitsWithZero :
          bitsWithZero.size() > bitsWithOne.size() ? bitsWithOne : bitsWithZero;
    }
    System.out.println(type + ": " + Integer.parseInt(fileRows.get(0), 2));
    return fileRows.get(0);
  }

  private Map<String, Integer> getAmountOfBitsInEachVerticalRow(List<String> fileRow, int index) {
    int zeros = 0;
    int ones = 0;
    for (String bits : fileRow) {
      int bit = Character.getNumericValue(bits.charAt(index));
      if (bit == 0) zeros++;
      else if (bit == 1) ones++;
    }
    return Map.of("zeros", zeros, "ones", ones);
  }
}
