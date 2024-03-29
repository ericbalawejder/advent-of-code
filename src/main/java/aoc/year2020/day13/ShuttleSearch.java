package aoc.year2020.day13;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ShuttleSearch {

  public static void main(String[] args) {
    final List<String> notes = readFile("src/main/java/aoc/year2020/day13/notes.txt");
    System.out.println(calculateBusValue(notes));
    System.out.println(calculateSubsequentBusTimestamp(notes));
  }

  // https://en.wikipedia.org/wiki/Chinese_remainder_theorem
  // https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
  static long calculateSubsequentBusTimestamp(List<String> notes) {
    final String[] buses = Arrays.stream(notes.get(1).split(","))
        .toArray(String[]::new);

    final long[][] busesInService = IntStream.range(0, buses.length)
        .filter(i -> !buses[i].equals("x"))
        .mapToObj(i -> new long[]{i, Long.parseLong(buses[i])})
        .toArray(long[][]::new);

    final long productOfPrimes = Arrays.stream(busesInService)
        .mapToLong(a -> a[1])
        .reduce(1L, Math::multiplyExact);

    final long summation = Arrays.stream(busesInService)
        .mapToLong(a -> a[0] * (productOfPrimes / a[1])
            * modularMultiplicativeInverse(productOfPrimes / a[1], a[1]))
        .sum();

    final long uniqueSolution = Math.floorMod(summation, productOfPrimes);

    return productOfPrimes - uniqueSolution;
  }

  private static long modularMultiplicativeInverse(long n, long modulus) {
    if (gcd(n, modulus) != 1) {
      throw new IllegalArgumentException("Error: n and modulus must be coprime.");
    }
    return LongStream.range(1, modulus)
        .filter(i -> Math.floorMod(n * i, modulus) == 1)
        .findFirst()
        .orElseThrow();
  }

  private static long gcd(long a, long b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  static int calculateBusValue(List<String> notes) {
    final int timestamp = Integer.parseInt(notes.get(0));

    final List<Integer> buses = Arrays.stream(notes.get(1).split(","))
        .filter(s -> !s.equals("x"))
        .map(Integer::parseInt)
        .toList();

    int minimumTime = Integer.MAX_VALUE;
    int busId = 0;
    for (int bus : buses) {
      if (timestamp % bus == 0) {
        return 0;
      }
      int factor = timestamp / bus;
      int difference = (factor + 1) * bus - timestamp;
      if (difference < minimumTime) {
        minimumTime = difference;
        busId = bus;
      }
    }
    return busId * minimumTime;
  }

  private static long modularMultiplicativeInverse2(long n, long modulus) {
    try {
      final BigInteger a = new BigInteger(String.valueOf(n));
      final BigInteger m = new BigInteger(String.valueOf(modulus));
      return a.modInverse(m).longValue();
    } catch (Exception e) {
      throw new IllegalArgumentException("Error: n and modulus must be coprime.");
    }
  }

  private static List<String> readFile(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.toList();
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

}
