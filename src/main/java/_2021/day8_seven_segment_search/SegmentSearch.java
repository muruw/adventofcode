package _2021.day8_seven_segment_search;

import _2021.util.Utils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SegmentSearch {

  public static void main(String[] args) throws URISyntaxException, IOException {
    List<String> inputRows = Utils.readLinesFromResourceFile("/2021/segmentsearch-input", SegmentSearch.class);
    //task1(inputRows);
    System.out.println(task2(inputRows));
  }

  private static String sortStringAlphabetically(String keyword) {
    char[] chars = keyword.toCharArray();
    Arrays.sort(chars);
    return new String(chars);
  }

  private static long task2(List<String> segmentRows) {
    long sum = 0;
    for (String row : segmentRows) {
      StringBuilder rightSideSum = new StringBuilder();
      String[] leftSide = row.trim().split("\\|")[0].split(" ");
      String[] rightSide = row.trim().split("\\|")[1].split(" ");
      Map<Integer, String> decodedValues = signalWireMapper(List.of(leftSide));
      Map<String, Integer> swappedValues = decodedValues.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
      for (String valueToDecode : rightSide) {
        if (valueToDecode.length() != 0) {
          rightSideSum.append(swappedValues.get(sortStringAlphabetically(valueToDecode)));
        }
      }
      System.out.println(rightSideSum);
      sum += Integer.parseInt(rightSideSum.toString());

    }
    return sum;
  }

  private static boolean charactersAreIncludedInString(char[] chars, String string) {
    int checked = 0;
    for (Character ch : chars) {
      if (string.indexOf(ch) != -1){
        checked++;
      }
    }
    return checked == chars.length;
  }

  private static Map<Integer, String> signalWireMapper(List<String> originalDigit) {
    Map<Integer, String> decodedDigitValues = new HashMap<>();
    /*
      0 has 6 segments and
      1 is unique and has 2 segments
      2 has 5 segments and 2 segments are not found in 5
      3 has 5 segments and contains both c and f
      4 is unique and has 4 segments
      5 has 5 segments and 2 segments are not found in 5
      6 has 6 segments and does not have all segments of 7
      7 is unique and has 3 segments
      8 is unique and has 7 segments
      9 has 6 segments and all segments like 3
     */
    List<String> digit = new ArrayList<>();
    for (String s : originalDigit) {
      digit.add(sortStringAlphabetically(s));
    }
    decodedDigitValues.put(1, (digit.stream().filter(seg -> seg.length() == 2).findFirst().get()));
    decodedDigitValues.put(7, (digit.stream().filter(seg -> seg.length() == 3).findFirst().get()));
    decodedDigitValues.put(4, (digit.stream().filter(seg -> seg.length() == 4).findFirst().get()));
    decodedDigitValues.put(8, (digit.stream().filter(seg -> seg.length() == 7).findFirst().get()));

    System.out.println("7: " + decodedDigitValues.get(7));
    decodedDigitValues.put(6, (digit.stream().filter(seg -> seg.length() == 6 && !charactersAreIncludedInString(decodedDigitValues.get(7).toCharArray(), seg))).findFirst().get());
    // Find c ( top right )
    // find f (in 1 but not C)
    Character c;
    Character f;
    if (decodedDigitValues.get(6).indexOf(decodedDigitValues.get(1).charAt(0)) == -1) {
      c = decodedDigitValues.get(1).charAt(0);
      f = decodedDigitValues.get(1).charAt(1);
    } else {
      c = decodedDigitValues.get(1).charAt(1);
      f = decodedDigitValues.get(1).charAt(0);
    }

    // find 2
    Character finalC = c;
    Character finalF = f;
    decodedDigitValues.put(2, (digit.stream().filter(seg -> seg.length() == 5 && seg.indexOf(finalC) != -1 && seg.indexOf(finalF) == -1).findFirst().get()));
    // find 3
    decodedDigitValues.put(3, (digit.stream().filter(seg -> seg.length() == 5 && seg.indexOf(finalC) != -1 && seg.indexOf(finalF) != -1).findFirst().get()));
    // find 5
    decodedDigitValues.put(5, (digit.stream().filter(seg -> seg.length() == 5 && seg.indexOf(finalC) == -1 && seg.indexOf(finalF) != -1).findFirst().get()));

    // find e to find 9 and then find the last remaining 0
    Character e = null;
    // I know that digit 6 has 6 segments and digit 5 has 5 segments
    for (int i = 0; i < decodedDigitValues.get(5).length(); i++) {
      if (decodedDigitValues.get(6).charAt(i) != decodedDigitValues.get(5).charAt(i)) {
        e = decodedDigitValues.get(6).charAt(i);
        break;
      }
      e = decodedDigitValues.get(6).charAt(5);
    }
    Character finalE = e;
    decodedDigitValues.put(9, (digit.stream().filter(seg ->  seg.length() == 6 && !decodedDigitValues.containsValue(seg) && seg.indexOf(finalE) == -1).findFirst().get()));
    //find 9
    decodedDigitValues.put(0, (digit.stream().filter(seg -> !decodedDigitValues.containsValue((seg))).findFirst().get()));
    return decodedDigitValues;
  }

  private static int task1(List<String> segmentRows) {
    int amountOfEasyDigits = 0;
    for (String row : segmentRows) {
      String[] digits = row.trim().split("\\|")[1].split(" ");
      amountOfEasyDigits += findEasyDigits(digits).size();
    }
    System.out.println(amountOfEasyDigits);
    return amountOfEasyDigits;
  }

  private static List<String> findEasyDigits(String[] digits) {
    List<String> easyDigits = new ArrayList<>();
    for (String digit : digits) {
      if (digit.length() != 0 && digit.length() <= 4 || digit.length() == 7) easyDigits.add(digit);
    }
    System.out.println(easyDigits);
    return easyDigits;
  }
}
