package aoc.year2020.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TwoSum {

  public static void main(String[] args) {
    List<Integer> values = readFile("src/main/java/aoc/year2020/day1/day1input.txt");
    System.out.println(findSum(values, 2020));
    System.out.println(listProduct(findSum(values, 2020)));
  }

  static List<Integer> listProduct(List<List<Integer>> lists) {
    return lists.stream()
        .map(list -> list.stream()
            .reduce(1, Math::multiplyExact))
        .toList();
  }

  static List<List<Integer>> findSum(List<Integer> list, int target) {
    List<List<Integer>> values = new ArrayList<>();
    Map<Integer, Integer> map = new HashMap<>();
    for (Integer integer : list) {
      if (map.containsKey(integer)) {
        values.add(Arrays.asList(map.get(integer), integer));
      } else {
        map.put(target - integer, integer);
      }
    }
    return Collections.unmodifiableList(values);
  }

  private static List<Integer> readFile(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(Integer::parseInt)
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

}
