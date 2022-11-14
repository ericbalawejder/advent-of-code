package aoc.year2021.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TheTreacheryOfWhales {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day7/crab-horizontal-position.txt";
    final List<Integer> crabPosition = getCrabPosition(path);
    System.out.println(fuelUsed(crabPosition));
    System.out.println(fuelUsedPart2(crabPosition));
  }

  static int fuelUsedPart2(List<Integer> crabPosition) {
    final List<Integer> means = means(crabPosition);
    return means.stream()
        .map(mean -> crabPosition.stream()
            .map(i -> sigmaN(Math.abs(i - mean)))
            .reduce(0, Integer::sum))
        .toList()
        .stream()
        .min(Integer::compareTo)
        .orElse(0);
  }

  static int fuelUsed(List<Integer> crabPosition) {
    final List<Integer> medians = medians(crabPosition);
    return medians.stream()
        .map(median -> crabPosition.stream()
            .map(i -> Math.abs(i - median))
            .reduce(0, Integer::sum))
        .toList()
        .stream()
        .min(Integer::compareTo)
        .orElse(0);
  }

  private static int sigmaN(int n) {
    return n * (n + 1) / 2;
  }

  private static List<Integer> medians(List<Integer> numbers) {
    return numbers.size() % 2 == 0 ?
        List.of(numbers.get(numbers.size() / 2), numbers.get(numbers.size() / 2 - 1)) :
        List.of(numbers.get(numbers.size() / 2));
  }

  private static List<Integer> means(List<Integer> numbers) {
    final double mean = numbers.stream()
        .reduce(0, Integer::sum) / (1.0 * numbers.size());

    return List.of((int) Math.floor(mean), (int) Math.ceil(mean));
  }

  private static List<Integer> getCrabPosition(String path) {
    try {
      final String data = Files.readString(Path.of(path));
      return Arrays.stream(data.split(","))
          .map(Integer::parseInt)
          .sorted()
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
