package aoc.utils;

import java.util.List;
import java.util.stream.Stream;

public class ListUtils {

    private static <T> List<T> symmetricDifference(final List<T> listA, final List<T> listB) {
        return difference(union(listA, listB), intersection(listA, listB));
    }

    private static <T> List<T> union(final List<T> listA, final List<T> listB) {
        return Stream.concat(listA.stream(), listB.stream())
                .toList();
    }

    private static <T> List<T> difference(final List<T> listA, final List<T> listB) {
        return listA.stream()
                .filter(e -> !listB.contains(e))
                .toList();
    }

    private static <T> List<T> intersection(final List<T> listA, final List<T> listB) {
        return listA.stream()
                .filter(listB::contains)
                .toList();
    }

}
