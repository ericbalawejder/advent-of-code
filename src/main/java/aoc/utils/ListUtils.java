package aoc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
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

  private static <T> List<List<T>> partition(List<T> things, int size) {
    if (Math.floorMod(things.size(), size) != 0) {
      throw new IllegalArgumentException("partition is uneven");
    }
    return things.stream()
        .collect(
            Collector.of(
                ArrayList::new,
                (list, item) -> {
                  if (list.isEmpty() || list.getLast().size() == size) {
                    list.add(new ArrayList<>());
                  }
                  list.getLast().add(item);
                },
                (list1, list2) -> {
                  list1.getLast().addAll(list2.removeFirst());
                  list1.addAll(list2);
                  return list1;
                }
            ));
  }

  private static List<Interval> mergeIntervals(List<Interval> intervals) {
    if (intervals == null || intervals.isEmpty()) {
      return intervals;
    }
    final List<Interval> combinedIntervals = new ArrayList<>();
    final List<Interval> sortedIntervals = intervals.stream()
        .sorted()
        .toList();

    Interval current = sortedIntervals.getFirst();
    for (int i = 1; i < sortedIntervals.size(); i++) {
      Interval interval = sortedIntervals.get(i);
      if (current.b() >= interval.a()) {
        current = new Interval(current.a(), Math.max(current.b(), interval.b()));
      } else {
        combinedIntervals.add(current);
        current = interval;
      }
    }
    combinedIntervals.add(current);

    return List.copyOf(combinedIntervals);
  }

  record Interval(long a, long b) implements Comparable<Interval> {

    Interval {
      if (b <= a) {
        throw new IllegalArgumentException("interval must be strictly increasing");
      }
    }

    long distance() {
      return Math.abs(b - a);
    }

    @Override
    public int compareTo(Interval o) {
      return Long.compare(this.a(), o.a());
    }

  }

}
