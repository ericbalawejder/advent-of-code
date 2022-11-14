package aoc.year2021.day3;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BinaryDiagnosticPart2 {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day3/diagnostic-report.txt";
    final List<String> diagnosticReport = readFile(path);
    System.out.println(lifeSupportRating(diagnosticReport));
  }

  static int lifeSupportRating(List<String> diagnosticReport) {
    return oxygenGeneratorRating(diagnosticReport) * cO2ScrubberRating(diagnosticReport);
  }

  private static int oxygenGeneratorRating(List<String> diagnosticReport) {
    final int length = diagnosticReport.get(0).length();
    List<String> report = new ArrayList<>(diagnosticReport);

    for (int i = 0; i < length; i++) {
      final int index = i;
      if (report.size() > 1) {
        final char majorityBit = findMajorityBit(report, i);
        report = report.stream()
            .filter(line -> line.charAt(index) == majorityBit)
            .collect(Collectors.toList());
      }
    }
    return Integer.parseInt(report.get(0), 2);
  }

  private static int cO2ScrubberRating(List<String> diagnosticReport) {
    final int length = diagnosticReport.get(0).length();
    List<String> report = new ArrayList<>(diagnosticReport);

    for (int i = 0; i < length; i++) {
      final int index = i;
      if (report.size() > 1) {
        final String bit = onesComplement(String.valueOf(findMajorityBit(report, i)));
        final char majorityBit = bit.charAt(0);
        report = report.stream()
            .filter(line -> line.charAt(index) == majorityBit)
            .collect(Collectors.toList());
      }
    }
    return Integer.parseInt(report.get(0), 2);
  }

  private static char findMajorityBit(List<String> diagnosticReport, int index) {
    final int length = diagnosticReport.size();
    final int majoritySize = (int) Math.ceil(length / 2.0);
    final List<Character> majorityBit = diagnosticReport.stream()
        .map(s -> s.charAt(index))
        .filter(c -> c.equals('1'))
        .toList();

    return majorityBit.size() >= majoritySize ? '1' : '0';
  }

  private static String onesComplement(String binary) {
    final BigInteger powerOfTwoLength = new BigInteger("2").pow(binary.length());
    return powerOfTwoLength
        .add(new BigInteger(binary, 2).not()).toString(2);
  }

  private static List<String> readFile(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
