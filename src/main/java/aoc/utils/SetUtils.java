package aoc.utils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetUtils {

    private static <T> Set<T> symmetricDifference(final Set<T> setA, final Set<T> setB) {
        return difference(union(setA, setB), intersection(setA, setB));
    }

    private static <T> Set<T> union(final Set<T> setA, final Set<T> setB) {
        return Stream.concat(setA.stream(), setB.stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    private static <T> Set<T> difference(final Set<T> setA, final Set<T> setB) {
        return setA.stream()
                .filter(e -> !setB.contains(e))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static <T> Set<T> intersection(final Set<T> setA, final Set<T> setB) {
        return setA.stream()
                .filter(setB::contains)
                .collect(Collectors.toUnmodifiableSet());
    }

}
