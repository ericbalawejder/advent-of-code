package aoc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utilities {

    public static List<Integer> modes(List<Integer> numbers) {
        final Map<Integer, Integer> countFrequencies = numbers.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.reducing(0, e -> 1, Integer::sum)));

        final int maxFrequency = countFrequencies.values()
                .stream()
                .mapToInt(count -> count)
                .max()
                .orElse(0);

        return countFrequencies.entrySet()
                .stream()
                .filter(e -> e.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static List<Integer> median(List<Integer> numbers) {
        return numbers.size() % 2 == 0 ? List.of(numbers.get(numbers.size() / 2),
                numbers.get(numbers.size() / 2 - 1)) : List.of(numbers.get(numbers.size() / 2));
    }

    public static double mean(List<Integer> numbers) {
        return numbers.stream()
                .reduce(0, Integer::sum) / (1.0 * numbers.size());
    }

    public static int round(double value) {
        final BigDecimal bigdecimal = new BigDecimal(Double.toString(value));
        return bigdecimal.setScale(0, RoundingMode.HALF_UP)
                .intValue();
    }

    // In case of one-to-one relationship, you can return the first matched key.
    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}
