package aoc.year2023.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.LongStream;

class IfYouGiveASeedAFertilizer {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2023/day5/almanac.txt";
    final Almanac almanac = buildAlmanac(path);

    final long lowestLocationNumber = findLowestLocationNumber(almanac);
    System.out.println(lowestLocationNumber);

    final long lowestLocationNumber2 = findLowestLocationNumberOfSeedRanges(almanac);
    System.out.println(lowestLocationNumber2);
  }

  static long findLowestLocationNumberOfSeedRanges(Almanac almanac) {
    final List<Garden> gardens = almanac.gardens();
    final List<List<Long>> seedRanges = partition(almanac.seeds(), 2);
    final List<Interval> intervals = seedRanges.stream()
        .map(list -> new Interval(list.getFirst(), list.getFirst() + list.getLast() - 1))
        .sorted()
        .toList();

    return intervals.stream()
        .flatMapToLong(interval -> LongStream.rangeClosed(interval.a(), interval.b()))
        .mapToObj(seed -> {
          final AtomicLong locationNumber = new AtomicLong(seed);
          gardens.forEach(garden -> locationNumber.set(garden.compute(locationNumber.get())));
          return locationNumber;
        })
        .mapToLong(AtomicLong::get)
        .min()
        .orElseThrow();
  }

  static long findLowestLocationNumber(Almanac almanac) {
    final List<Garden> gardens = almanac.gardens();
    final List<Long> seeds = almanac.seeds();

    return seeds.stream()
        .map(seed -> {
          final AtomicLong locationNumber = new AtomicLong(seed);
          gardens.forEach(garden -> locationNumber.set(garden.compute(locationNumber.get())));
          return locationNumber;
        })
        .mapToLong(AtomicLong::get)
        .min()
        .orElseThrow();
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

  private static Almanac buildAlmanac(String path) {
    final List<String> plans = getAlmanac(path);
    final List<Long> seeds = getSeeds(plans);
    final List<Garden> gardens = plans.stream()
        .skip(1)
        .map(IfYouGiveASeedAFertilizer::processGarden)
        .toList();

    return new Almanac(seeds, gardens);
  }

  private static Garden processGarden(String mapping) {
    final String[] parts = mapping.split("map:");
    final String name = parts[0].trim();
    final List<List<Long>> values = Arrays.stream(parts[1].split("\n"))
        .filter(Predicate.not(String::isBlank))
        .map(s -> Arrays.stream(s.split(" "))
            .map(Long::parseLong)
            .toList())
        .toList();

    final List<Range> ranges = values.stream()
        .map(list -> new Range(list.get(0), list.get(1), list.get(2)))
        .toList();

    return new Garden(name, ranges);
  }

  private static List<Long> getSeeds(List<String> almanac) {
    return Arrays.stream(almanac.getFirst()
            .split(":")[1]
            .trim()
            .split(" "))
        .map(Long::parseLong)
        .toList();
  }

  private static List<String> getAlmanac(String path) {
    try {
      final String data = Files.readString(Path.of(path));
      return Arrays.stream(data.split("\n\n"))
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
