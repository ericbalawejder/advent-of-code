package aoc.year2021.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SonarSweep {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day1/sonar-sweep-report.txt";
    final List<Integer> sonarSweepReport = readFile(path);
    System.out.println(countIncreaseInDepth(sonarSweepReport));
    System.out.println(countContiguousIncreaseInDepth(sonarSweepReport, 1));
    System.out.println(countContiguousIncreaseInDepth(sonarSweepReport, 3));
  }

  static int countContiguousIncreaseInDepth(List<Integer> sonarSweepReport, int length) {
    int count = 0;
    for (int i = 1; i < sonarSweepReport.size() - length + 1; i++) {
      final List<Integer> previous = sonarSweepReport.subList(i - 1, i + length - 1);
      final List<Integer> current = sonarSweepReport.subList(i, i + length);
      if (sum(current) > sum(previous)) {
        count++;
      }
    }
    return count;
  }

  static int countIncreaseInDepth(List<Integer> sonarSweepReport) {
    int count = 0;
    for (int i = 1; i < sonarSweepReport.size(); i++) {
      final int previous = sonarSweepReport.get(i - 1);
      final int current = sonarSweepReport.get(i);
      if (current > previous) {
        count++;
      }
    }
    return count;
  }

  private static int sum(List<Integer> list) {
    return list.stream().reduce(0, Integer::sum);
  }

  private static List<Integer> readFile(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(Integer::parseInt).toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
