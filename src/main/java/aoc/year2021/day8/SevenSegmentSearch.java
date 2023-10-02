package aoc.year2021.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SevenSegmentSearch {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day8/signal-patterns.txt";
    final List<List<List<String>>> signalAndOutputValues = getSignalAndOutputValues(path);
    final List<List<String>> outputValues = signalAndOutputValues.get(1);
    final List<List<String>> signalPatterns = signalAndOutputValues.get(0);

    System.out.println(countUniqueOutputValues(outputValues));
    System.out.println(sumDecodedOutputValues(signalPatterns, outputValues));
  }

  static int countUniqueOutputValues(List<List<String>> outputValues) {
    return (int) outputValues.stream()
        .flatMap(List::stream)
        .filter(s -> s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7)
        .count();
  }

  static int sumDecodedOutputValues(List<List<String>> signalPatterns, List<List<String>> outputValues) {
    return IntStream.range(0, signalPatterns.size())
        .mapToObj(i -> decodeOutput(signalPatterns.get(i), outputValues.get(i)))
        .map(Integer::parseInt)
        .reduce(0, Integer::sum);
  }

  private static String decodeOutput(List<String> signalPatterns, List<String> outputValues) {
    final Map<String, Set<Character>> signalValues = decodeSignalValues(signalPatterns);

    return outputValues.stream()
        .map(s -> getKeysByValue(signalValues, stringToCharacterSet(s)))
        .flatMap(Set::stream)
        .collect(Collectors.joining());
  }

  // Using the map from decodeUniqueSignalValues(), use deduction based on the known patterns
  // from the digits (1, 4, 7, 8) to find the unknown digits and place them in the map.
  private static Map<String, Set<Character>> decodeSignalValues(List<String> signalPatterns) {
    final Map<String, Set<Character>> signalValues = new HashMap<>(decodeUniqueSignalValues(signalPatterns));
    for (String signal : signalPatterns) {
      final Set<Character> digit = stringToCharacterSet(signal);
      if (signal.length() == 5) {
        if (digit.containsAll(signalValues.get("1"))) {
          signalValues.put("3", digit);
        } else if (digit.containsAll(difference(signalValues.get("4"), signalValues.get("1")))) {
          signalValues.put("5", digit);
        } else if (digit.containsAll(difference(signalValues.get("8"), signalValues.get("4")))) {
          signalValues.put("2", digit);
        }
      } else if (signal.length() == 6) {
        if (digit.containsAll(signalValues.get("4"))) {
          signalValues.put("9", digit);
        } else if (digit.containsAll(difference(signalValues.get("8"), signalValues.get("1")))) {
          signalValues.put("6", digit);
        } else if (digit.containsAll(
            union(difference(signalValues.get("8"), signalValues.get("4")), signalValues.get("1")))) {
          signalValues.put("0", digit);
        }
      }
    }
    return Map.copyOf(signalValues);
  }

  // Place the known digits in the map with their known values.
  private static Map<String, Set<Character>> decodeUniqueSignalValues(List<String> signalPatterns) {
    final Map<String, Set<Character>> signalValues = new HashMap<>();
    for (String signal : signalPatterns) {
      final Set<Character> digit = stringToCharacterSet(signal);
      if (signal.length() == 2) {
        signalValues.put("1", digit);
      } else if (signal.length() == 3) {
        signalValues.put("7", digit);
      } else if (signal.length() == 4) {
        signalValues.put("4", digit);
      } else if (signal.length() == 7) {
        signalValues.put("8", digit);
      }
    }
    return Map.copyOf(signalValues);
  }

  private static Set<Character> stringToCharacterSet(String word) {
    return word.chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.toUnmodifiableSet());
  }

  private static <T> Set<T> difference(final Set<T> setA, final Set<T> setB) {
    return setA.stream()
        .filter(e -> !setB.contains(e))
        .collect(Collectors.toUnmodifiableSet());
  }

  private static <T> Set<T> union(final Set<T> setA, final Set<T> setB) {
    return Stream.concat(setA.stream(), setB.stream())
        .collect(Collectors.toUnmodifiableSet());
  }

  public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
    return map.entrySet()
        .stream()
        .filter(entry -> Objects.equals(entry.getValue(), value))
        .map(Map.Entry::getKey)
        .collect(Collectors.toUnmodifiableSet());
  }

  private static List<List<List<String>>> getSignalAndOutputValues(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(s -> s.split(" \\| "))
          .map(Arrays::asList)
          .collect(
              Collectors.teeing(
                  Collectors.mapping(list -> processLines(list.get(0)), Collectors.toList()),
                  Collectors.mapping(list -> processLines(list.get(1)), Collectors.toList()),
                  List::of
              ));
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static List<String> processLines(String signals) {
    return Arrays.stream(signals.trim().split(" ")).toList();
  }

}
