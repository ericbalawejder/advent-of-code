package aoc.year2021.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Lanternfish {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day6/lanternfish-ages.txt";
    final List<Integer> agesOfFish = getFish(path);
    System.out.println(countFish(agesOfFish, 80));
    System.out.println(countFish(agesOfFish, 256));
  }

  // Roll the array.
  static long countFish(List<Integer> agesOfFish, int days) {
    final long[] fishCount = new long[9];
    agesOfFish.forEach(i -> fishCount[i]++);
    int base = 0;
    for (int day = 0; day < days; day++) {
      fishCount[(base + 7) % 9] += fishCount[base];
      base = (base + 1) % 9;
    }
    return Arrays.stream(fishCount).sum();
  }

  private static List<Integer> getFish(String path) {
    try {
      final String data = Files.readString(Path.of(path));
      return Arrays.stream(data.split(","))
          .map(Integer::parseInt)
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
