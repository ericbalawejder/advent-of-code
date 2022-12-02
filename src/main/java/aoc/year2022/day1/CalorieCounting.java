package aoc.year2022.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

class CalorieCounting {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2022/day1/calories.txt";
    final SortedSet<Integer> calories = countCalories(path);
    System.out.println(findMost(calories));
    System.out.println(sumOfTopNElves(calories, 3));
  }

  static Integer sumOfTopNElves(SortedSet<Integer> food, int n) {
    final int size = food.size();
    if (n > size) throw new IllegalArgumentException("n is out of array bounds");
    final List<Integer> calorieCount = new ArrayList<>(food);
    return calorieCount.subList(size - n, size)
        .stream()
        .reduce(0, Integer::sum);
  }

  static Integer findMost(SortedSet<Integer> food) {
    return food.last();
  }

  private static SortedSet<Integer> countCalories(String path) {
    try {
      final String data = Files.readString(Path.of(path));
      return Arrays.stream(data.split("\n\n"))
          .map(s -> s.replace("\n", " "))
          .map(s -> Arrays.stream(s.split(" "))
              .map(Integer::parseInt)
              .reduce(0, Integer::sum))
          .collect(Collectors.toCollection(TreeSet::new));
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptySortedSet();
    }
  }

}
