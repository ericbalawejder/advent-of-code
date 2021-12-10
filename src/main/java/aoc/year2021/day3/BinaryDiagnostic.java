package aoc.year2021.day3;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BinaryDiagnostic {

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/year2021/day3/diagnostic-report.txt";
        final List<Integer> diagnosticReport = readFile(path);
        System.out.println(powerConsumption(diagnosticReport));
    }

    static int powerConsumption(List<Integer> diagnosticReport) {
        final int binaryLength = max(diagnosticReport);
        final int majorityBit = diagnosticReport.size() / 2;
        final StringBuilder binary = new StringBuilder(binaryLength);

        for (int i = binaryLength - 1; i >= 0; i--) {
            int count = 0;
            for (int number : diagnosticReport) {
                if ((number & (int) Math.pow(2, i)) > 0) {
                    count++;
                }
            }
            binary.append((count > majorityBit ? "1" : "0"));
        }
        final String binaryGammaRate = binary.toString();
        final int epsilonRate = onesComplement(binaryGammaRate);
        final int gammaRate = Integer.parseInt(binaryGammaRate, 2);
        return gammaRate * epsilonRate;
    }

    private static int onesComplement(String binary) {
        final BigInteger powerOfTwoLength = new BigInteger("2").pow(binary.length());
        final String onesComplement = powerOfTwoLength
                .add(new BigInteger(binary, 2).not()).toString(2);
        return Integer.parseInt(onesComplement, 2);
    }

    private static int max(List<Integer> number) {
        return number.stream()
                .max(Integer::compareTo)
                .map(i -> Integer.toBinaryString(i).length())
                .orElse(0);
    }

    private static List<Integer> readFile(String path) throws IOException {
        return Files.lines(Paths.get(path))
                .map(s -> Integer.parseInt(s, 2))
                .toList();
    }

}
