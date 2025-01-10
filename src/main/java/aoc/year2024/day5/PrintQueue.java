package aoc.year2024.day5;

import org.paukov.combinatorics3.Generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PrintQueue {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2024/day5/page-updates.txt";
    final List<String> sections = readSections(path);
    final Map<Integer, Set<Integer>> rules = getPageOrderingRules(sections);
    final List<List<Integer>> pages = getPageUpdates(sections);

    final int sum = sumOfValidMiddlePageNumbers(pages, rules);
    System.out.println(sum);

    final int sum2 = sumOfInvalidMiddlePageNumbers(pages, rules);
    System.out.println(sum2);
  }

  static int sumOfInvalidMiddlePageNumbers(List<List<Integer>> pages, Map<Integer, Set<Integer>> rules) {
    return pages.stream()
        .filter(page -> !isCorrectOrdering(page, rules))
        .map(page -> reorderInvalidPages(page, rules))
        .map(PrintQueue::getMiddlePageNumber)
        .reduce(0, Integer::sum);
  }

  static int sumOfValidMiddlePageNumbers(List<List<Integer>> pages, Map<Integer, Set<Integer>> rules) {
    return pages.stream()
        .filter(page -> isCorrectOrdering(page, rules))
        .map(PrintQueue::getMiddlePageNumber)
        .reduce(0, Integer::sum);
  }

  private static List<Integer> reorderInvalidPages(List<Integer> pages, Map<Integer, Set<Integer>> rules) {
    final Predicate<Rule> isNonValidOrdering = rule ->
        rules.get(rule.x()) != null && !rules.get(rule.x()).contains(rule.y()) ||
            rules.get(rule.x()) == null && rules.get(rule.y()) != null && rules.get(rule.y()).contains(rule.x());

    final List<Rule> validRules = generateAllPossibleRules(pages)
        .stream()
        .map(rule -> isNonValidOrdering.test(rule) ? new Rule(rule.y(), rule.x()) : rule)
        .toList();

    final Map<Integer, Integer> xValueByCount =  validRules.stream()
        .collect(Collectors.groupingBy(Rule::x, Collectors.summingInt(e -> 1)));

    final List<Rule> sortedRules = validRules.stream()
        .sorted(Comparator.comparing((Rule rule) -> xValueByCount.get(rule.x())).reversed())
        .toList();

    final List<Integer> assemblePages = sortedRules.stream()
        .map(Rule::x)
        .toList();

    return Stream.concat(assemblePages.stream(), Stream.of(sortedRules.getLast().y()))
        .distinct()
        .toList();
  }

  private static int getMiddlePageNumber(List<Integer> pages) {
    if (pages == null || pages.isEmpty()) throw new IllegalArgumentException("pages is empty");
    return pages.get(pages.size() / 2);
  }

  private static boolean isCorrectOrdering(List<Integer> pages, Map<Integer, Set<Integer>> rules) {
    final Predicate<Rule> isNonValidOrdering = rule ->
        rules.get(rule.x()) != null && !rules.get(rule.x()).contains(rule.y()) ||
            rules.get(rule.x()) == null && rules.get(rule.y()) != null && rules.get(rule.y()).contains(rule.x());

    return generateAllPossibleRules(pages)
        .stream()
        .noneMatch(isNonValidOrdering);
  }

  private static List<Rule> generateAllPossibleRules(List<Integer> pageNumbers) {
    return Generator.combination(pageNumbers)
        .simple(2)
        .stream()
        .map(list -> new Rule(list.getFirst(), list.getLast()))
        .toList();
  }

  private static Map<Integer, Set<Integer>> getPageOrderingRules(List<String> lines) {
    return Arrays.stream(lines.getFirst().split("\n"))
        .map(line -> new Rule(Integer.parseInt(line.split("\\|")[0]), Integer.parseInt(line.split("\\|")[1])))
        .collect(Collectors.groupingBy(Rule::x, Collectors.mapping(Rule::y, Collectors.toUnmodifiableSet())));
  }

  private static List<List<Integer>> getPageUpdates(List<String> lines) {
    return Arrays.stream(lines.getLast().split("\n"))
        .map(String::trim)
        .map(line -> Arrays.stream(line.split(","))
            .map(Integer::parseInt)
            .toList())
        .toList();
  }

  private static List<String> readSections(String path) {
    try {
      final String data = Files.readString(Path.of(path));
      return Arrays.stream(data.split("\n\n")).toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
