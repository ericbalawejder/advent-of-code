package aoc.year2024.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class RedNosedReports {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2024/day2/unusual-data.txt";
    final List<List<Integer>> reports = getReports(path);
    final int safeReports = countSafeReports(reports);
    System.out.println(safeReports);

    final int safeDampenerReports = countSafeDampenedReports(reports);
    System.out.println(safeDampenerReports);
  }

  static int countSafeDampenedReports(List<List<Integer>> reports) {
    return reports.stream()
        .filter(RedNosedReports::isDampenerSafe)
        .mapToInt(e -> 1)
        .reduce(0, Integer::sum);
  }

  static int countSafeReports(List<List<Integer>> reports) {
    return reports.stream()
        .filter(RedNosedReports::isSafe)
        .mapToInt(e -> 1)
        .reduce(0, Integer::sum);
  }

  private static boolean isDampenerSafe(List<Integer> report) {
    return isSafe(report) || canTolerateSingleBadLevel(report);
  }

  private static boolean canTolerateSingleBadLevel(List<Integer> report) {
    return IntStream.range(0, report.size())
        .mapToObj(i -> {
          final List<Integer> newReport = new ArrayList<>(report);
          newReport.remove(i);
          return newReport;
        })
        .anyMatch(RedNosedReports::isSafe);
  }

  private static boolean isSafe(List<Integer> report) {
    return (isStrictlyIncreasing(report) || isStrictlyDecreasing(report)) && satisfiesEuclideanDistance(report);
  }

  private static boolean isStrictlyIncreasing(List<Integer> report) {
    if (report == null || report.size() < 2) {
      return true;
    }
    return IntStream.range(1, report.size())
        .allMatch(i -> report.get(i) > report.get(i - 1));
  }

  private static boolean isStrictlyDecreasing(List<Integer> report) {
    if (report == null || report.size() < 2) {
      return true;
    }
    return IntStream.range(1, report.size())
        .allMatch(i -> report.get(i) < report.get(i - 1));
  }

  private static boolean satisfiesEuclideanDistance(List<Integer> report) {
    if (report == null || report.size() < 2) {
      return true;
    }
    return IntStream.range(1, report.size())
        .allMatch(i -> Math.abs(report.get(i) - report.get(i - 1)) > 0 &&
            Math.abs(report.get(i) - report.get(i - 1)) < 4);
  }

  private static List<List<Integer>> getReports(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(s -> s.split(" "))
          .map(line -> Arrays.stream(line)
              .map(Integer::parseInt)
              .toList())
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
