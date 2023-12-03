package aoc.year2023.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class Trebuchet {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2023/day1/calibration-document.txt";
    final List<List<Integer>> calibrationValues = getCalibrationValues(path);
    final int sum = sumOfCalibrationValues(calibrationValues);
    System.out.println(sum);
  }

  static int sumOfCalibrationValues(List<List<Integer>> calibrationValues) {
    return calibrationValues.stream()
        .map(list -> String.valueOf(list.getFirst()) + list.getLast())
        .mapToInt(Integer::parseInt)
        .sum();
  }

  private static List<List<Integer>> getCalibrationValues(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(s -> s.replaceAll("\\D+", ""))
          .map(s -> s.chars()
              .mapToObj(c -> (char) c)
              .map(c -> Integer.parseInt(String.valueOf(c)))
              .toList())
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
