package aoc.year2022.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class CampCleanup {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2022/day4/section-assignments.txt";
    final List<List<Set<Integer>>> sectionAssignmentPairs = getSectionPairs(path);

    final int numberOfOverlappingRanges = findNumberOfOverlappingRanges(sectionAssignmentPairs);
    System.out.println(numberOfOverlappingRanges);

    final int numberOfIntersectingRanges = findNumberOfIntersectingRanges(sectionAssignmentPairs);
    System.out.println(numberOfIntersectingRanges);
  }

  static int findNumberOfIntersectingRanges(List<List<Set<Integer>>> sectionPairs) {
    return (int) sectionPairs.stream()
        .filter(Predicate.not(pair -> intersection(pair.getFirst(), pair.getLast()).isEmpty()))
        .count();
  }

  static int findNumberOfOverlappingRanges(List<List<Set<Integer>>> sectionPairs) {
    return (int) sectionPairs.stream()
        .filter(pair -> isSubset(pair.getFirst(), pair.getLast()))
        .count();
  }

  private static <T> Set<T> intersection(final Set<T> setA, final Set<T> setB) {
    return setA.stream()
        .filter(setB::contains)
        .collect(Collectors.toUnmodifiableSet());
  }

  private static <T> boolean isSubset(final Set<T> setA, final Set<T> setB) {
    return setA.containsAll(setB) || setB.containsAll(setA);
  }

  private static List<List<Set<Integer>>> getSectionPairs(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(s -> Arrays.stream(s.split(","))
              .map(CampCleanup::getSectionAssignments)
              .toList())
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static Set<Integer> getSectionAssignments(String range) {
    final String[] r = range.split("-");
    return IntStream.rangeClosed(Integer.parseInt(r[0]), Integer.parseInt(r[1]))
        .boxed()
        .collect(Collectors.toUnmodifiableSet());
  }

}
