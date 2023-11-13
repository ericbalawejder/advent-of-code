package aoc.year2022.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RucksackReorganization2 {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2022/day3/rucksack-items.txt";
    final List<SequencedSet<Set<Character>>> groupsOfThreeElves = getItemsInGroups(path, 3);
    final int sum = sumOfPriorities(groupsOfThreeElves);
    System.out.println(sum);
  }

  static int sumOfPriorities(List<SequencedSet<Set<Character>>> groupsOfThreeElves) {
    return groupsOfThreeElves.stream()
        .map(RucksackReorganization2::intersection)
        .flatMap(Set::stream)
        .map(Rucksack::getPriority)
        .reduce(0, Integer::sum);
  }

  private static Set<Character> intersection(SequencedSet<Set<Character>> groupOfSets) {
    return groupOfSets.stream()
        .skip(1)
        .collect(() -> new HashSet<>(groupOfSets.getFirst()), Set::retainAll, Set::retainAll);
  }

  private static List<SequencedSet<Set<Character>>> getItemsInGroups(String path, int groupSize) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(s -> s.chars()
              .mapToObj(c -> (char) c)
              .collect(Collectors.toUnmodifiableSet()))
          .collect(Collector.of(
              ArrayList::new,
              (groups, element) -> {
                if (groups.isEmpty() || groups.getLast().size() == groupSize) {
                  final SequencedSet<Set<Character>> current = new LinkedHashSet<>();
                  current.add(element);
                  groups.addLast(current);
                } else {
                  groups.getLast().add(element);
                }
              },
              (left, right) -> {
                throw new UnsupportedOperationException("Cannot be parallelized");
              }
          ));
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
