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
        final List<List<String>> outputValues = getOutputValues(path);
        final List<List<String>> signalPatterns = getSignalPatterns(path);
        System.out.println(countUniqueOutputValues(outputValues));
        System.out.println(sumDecodedOutputValues(signalPatterns, outputValues));
    }

    static int countUniqueOutputValues(List<List<String>> outputValues) {
        return (int) outputValues.stream()
                .flatMap(List::stream)
                .filter(s -> s.length() == 2 || s.length() == 3 ||
                        s.length() == 4 || s.length() == 7)
                .count();
    }

    static int sumDecodedOutputValues(List<List<String>> signalPatterns, List<List<String>> outputValues) {
        return decodeOutputValues(signalPatterns, outputValues)
                .stream()
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }

    private static List<String> decodeOutputValues(
            List<List<String>> signalPatterns, List<List<String>> outputValues) {

        return IntStream.range(0, signalPatterns.size())
                .mapToObj(i -> decodeOutput(signalPatterns.get(i), outputValues.get(i)))
                .toList();
    }

    private static String decodeOutput(List<String> signalPatterns, List<String> outputValues) {
        final Map<String, Set<Character>> signalValues =
                new HashMap<>(decodeSignalValues(signalPatterns));

        return outputValues.stream()
                .map(s -> getKeysByValue(signalValues, stringToSet(s)))
                .flatMap(Set::stream)
                .collect(Collectors.joining());
    }

    private static Map<String, Set<Character>> decodeSignalValues(List<String> signalPatterns) {
        final Map<String, Set<Character>> signalValues =
                new HashMap<>(decodeUniqueSignalValues(signalPatterns));
        for (String signal : signalPatterns) {
            if (signal.length() == 5) {
                final Set<Character> digit = signal.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toUnmodifiableSet());
                if (digit.containsAll(signalValues.get("1"))) {
                    signalValues.put("3", digit);
                } else if (digit.containsAll(difference(signalValues.get("4"), signalValues.get("1")))) {
                    signalValues.put("5", digit);
                } else if (digit.containsAll(difference(signalValues.get("8"), signalValues.get("4")))) {
                    signalValues.put("2", digit);
                } else {
                    throw new IllegalArgumentException("logic error");
                }
            } else if (signal.length() == 6) {
                final Set<Character> digit = signal.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toUnmodifiableSet());
                if (digit.containsAll(signalValues.get("4"))) {
                    signalValues.put("9", digit);
                } else if (digit.containsAll(difference(signalValues.get("8"), signalValues.get("1")))) {
                    signalValues.put("6", digit);
                } else if (digit.containsAll(
                        union(difference(signalValues.get("8"), signalValues.get("4")), signalValues.get("1")))) {
                    signalValues.put("0", digit);
                } else {
                    throw new IllegalArgumentException("logic error");
                }
            }
        }
        return Map.copyOf(signalValues);
    }

    private static Map<String, Set<Character>> decodeUniqueSignalValues(List<String> signalPatterns) {
        final Map<String, Set<Character>> signalValues = new HashMap<>();
        for (String signal : signalPatterns) {
            if (signal.length() == 2) {
                final Set<Character> one = signal.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toUnmodifiableSet());
                signalValues.put("1", one);
            } else if (signal.length() == 3) {
                final Set<Character> seven = signal.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toUnmodifiableSet());
                signalValues.put("7", seven);
            } else if (signal.length() == 4) {
                final Set<Character> four = signal.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toUnmodifiableSet());
                signalValues.put("4", four);
            } else if (signal.length() == 7) {
                final Set<Character> eight = signal.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.toUnmodifiableSet());
                signalValues.put("8", eight);
            }
        }
        return Map.copyOf(signalValues);
    }

    private static Set<Character> stringToSet(String word) {
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
                .collect(Collectors.toSet());
    }

    private static List<List<String>> getOutputValues(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(s -> s.split("\\|"))
                    .map(a -> a[1])
                    .map(String::trim)
                    .map(s -> s.split(" "))
                    .map(Arrays::asList)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static List<List<String>> getSignalPatterns(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(s -> s.split("\\|"))
                    .map(a -> a[0])
                    .map(String::trim)
                    .map(s -> s.split(" "))
                    .map(Arrays::asList)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static List<List<String>> readFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(s -> s.split(" \\| "))
                    .map(Arrays::asList)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
