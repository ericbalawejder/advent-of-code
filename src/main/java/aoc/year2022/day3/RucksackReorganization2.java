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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
      final List<String> items = stream.toList();
      if (Math.floorMod(items.size(), groupSize) != 0) {
        throw new IllegalArgumentException("group size");
      }
      final List<Set<String>> groups = new ArrayList<>();

      IntStream.iterate(0, i -> i <= items.size() - groupSize, i -> i + groupSize)
          .forEach(i -> groups.add(
              items.subList(i, i + groupSize)
                  .stream()
                  .collect(Collectors.toUnmodifiableSet())));

      return groups.stream()
          .map(RucksackReorganization2::convertToCharacters)
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static SequencedSet<Set<Character>> convertToCharacters(Set<String> groups) {
    return groups.stream()
        .map(s -> s.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toUnmodifiableSet()))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

}
