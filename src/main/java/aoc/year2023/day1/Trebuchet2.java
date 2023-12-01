package aoc.year2023.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Trebuchet2 {

  private static final String REGEX = "(zero|one|two|three|four|five|six|seven|eight|nine|[0-9])";
  private static final Pattern FIRST_DIGIT = Pattern.compile(REGEX);
  private static final Pattern LAST_DIGIT = Pattern.compile(".*" + REGEX);
  private static final Map<String, String> DIGITS = Map.of(
      "zero", "0",
      "one", "1",
      "two", "2",
      "three", "3",
      "four", "4",
      "five", "5",
      "six", "6",
      "seven", "7",
      "eight", "8",
      "nine", "9"
  );

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2023/day1/calibration-document.txt";
    final List<String> calibrationValues = getCalibrationDocument(path);
    final int sum = sumOfCalibrationValues(calibrationValues);
    System.out.println(sum);
  }

  static int sumOfCalibrationValues(List<String> calibrationValues) {
    return calibrationValues.stream()
        .map(Trebuchet2::extractDigits)
        .mapToInt(Integer::parseInt)
        .sum();
  }

  private static String extractDigits(String line) {
    final Matcher firstWord = FIRST_DIGIT.matcher(line);
    firstWord.find();
    final String firstDigit = firstWord.group(1);

    final Matcher lastWord = LAST_DIGIT.matcher(line);
    lastWord.find();
    final String lastDigit = lastWord.group(1);

    return DIGITS.getOrDefault(firstDigit, firstDigit) + DIGITS.getOrDefault(lastDigit, lastDigit);
  }

  private static List<String> getCalibrationDocument(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
