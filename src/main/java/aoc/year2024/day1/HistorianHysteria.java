package aoc.year2024.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class HistorianHysteria {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2024/day1/locations.txt";
    final List<List<Integer>> locationIds = importLists(path);
    final int totalDistance = computeEuclideanDistance(locationIds);
    System.out.println(totalDistance);

    final int similarityScore = computeSimilarityScore(locationIds);
    System.out.println(similarityScore);
  }

  static int computeSimilarityScore(List<List<Integer>> locationIds) {
    final List<Integer> left = locationIds.get(0);
    final List<Integer> right = locationIds.get(1);

    final Map<Integer, Integer> countById = right.stream()
        .collect(Collectors.groupingBy(i -> i, Collectors.summingInt(e -> 1)));

    return left.stream()
        .mapToInt(i -> countById.get(i) == null ? 0 : i * countById.get(i))
        .reduce(0, Integer::sum);
  }

  static int computeEuclideanDistance(List<List<Integer>> locationIds) {
    final List<Integer> left = locationIds.get(0);
    final List<Integer> right = locationIds.get(1);

    return IntStream.range(0, left.size())
        .map(i -> Math.abs(left.get(i) - right.get(i)))
        .reduce(0, Integer::sum);
  }

  private static List<List<Integer>> importLists(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(s -> s.split("\\s+"))
          .collect(Collector.of(
              () -> {
                List<List<Integer>> lists = new ArrayList<>();
                lists.add(new ArrayList<>());
                lists.add(new ArrayList<>());
                return lists;
              },
              (lists, item) -> {
                lists.get(0).add(Integer.valueOf(item[0]));
                lists.get(1).add(Integer.valueOf(item[1]));
              },
              (left, right) -> {
                left.get(0).addAll(right.get(0));
                left.get(1).addAll(right.get(1));
                return left;
              },
              lists -> {
                Collections.sort(lists.get(0));
                Collections.sort(lists.get(1));
                return lists;
              },
              Collector.Characteristics.UNORDERED
          ));
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
