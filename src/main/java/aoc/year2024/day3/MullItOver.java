package aoc.year2024.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class MullItOver {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2024/day3/memory.txt";
    final int sum = sumUncorruptedInstructions(path);
    System.out.println(sum);

    final int sumAdvanced = sumAdvancedUncorruptedInstructions(path);
    System.out.println(sumAdvanced);
  }

  static int sumAdvancedUncorruptedInstructions(String path) {
    final AtomicBoolean enabled = new AtomicBoolean(true);
    final String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)";
    final Pattern pattern = Pattern.compile(regex);
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(pattern::matcher)
          .map(matcher -> processAdvancedInstructions(matcher, enabled))
          .flatMap(List::stream)
          .reduce(0, Integer::sum);
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }
  }

  static int sumUncorruptedInstructions(String path) {
    final String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
    final Pattern pattern = Pattern.compile(regex);
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(pattern::matcher)
          .map(matcher -> {
            final List<Integer> result = new ArrayList<>();
            while (matcher.find()) {
              result.add(Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2)));
            }
            return result;
          })
          .flatMap(List::stream)
          .reduce(0, Integer::sum);
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }
  }

  private static List<Integer> processAdvancedInstructions(Matcher matcher, AtomicBoolean enabled) {
    final List<Integer> result = new ArrayList<>();
    while (matcher.find()) {
      if (matcher.group().equals("do()")) {
        enabled.set(true);
      } else if (matcher.group().equals("don't()")) {
        enabled.set(false);
      } else if (matcher.group().startsWith("mul") && enabled.get()) {
        result.add(Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2)));
      }
    }
    return result;
  }

}
