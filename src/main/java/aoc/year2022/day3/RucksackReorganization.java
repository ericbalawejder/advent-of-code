package aoc.year2022.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RucksackReorganization {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2022/day3/rucksack-items.txt";
    final List<Rucksack> items = getItems(path);
    final int sum = sumOfPriorities(items);
    System.out.println(sum);
  }

  static int sumOfPriorities(List<Rucksack> rucksack) {
    return rucksack.stream()
        .map(r -> Rucksack.intersection(r.compartment1(), r.compartment2()))
        .flatMap(Set::stream)
        .map(Rucksack::getPriority)
        .reduce(0, Integer::sum);
  }

  private static List<Rucksack> getItems(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(s -> new Rucksack(
              s.substring(0, s.length() / 2).chars().mapToObj(c -> (char) c).collect(Collectors.toUnmodifiableSet()),
              s.substring(s.length() / 2).chars().mapToObj(c -> (char) c).collect(Collectors.toUnmodifiableSet())))
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
