package aoc.year2023.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

class IfYouGiveASeedAFertilizer {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2023/day5/test.txt";
    final List<String> plans = getAlmanac(path);
    //System.out.println(plans);
    //final List<Integer> seeds = getSeeds(plans);
    //System.out.println(seeds);
    final Almanac almanac = buildAlmanac(plans);
    System.out.println(almanac);
  }

  static List<Integer> computeLocationNumbers(Almanac almanac) {
    // Function<T, R> computeSeed = computeRange...;
    // AtomicInteger seed
    // seeds.stream()
    //    .map(seed -> gardens.stream().map(g -> g.compute(seed))


    return Collections.emptyList();
  }

  // For each Garden 1, 2, 3... seed -> computeSeed -> location, location -> computeSeed -> location...
  private Integer computeLocationMappings(Integer seed) {
    return 0;
  }

  private static Almanac buildAlmanac(List<String> plans) {
    final List<Integer> seeds = getSeeds(plans);
    final List<Garden> gardens = plans.stream()
        .skip(1)
        .map(IfYouGiveASeedAFertilizer::processGarden)
        .toList();

    return new Almanac(seeds, gardens);
  }

  private static Garden processGarden(String mapping) {
    final String[] parts = mapping.split("map:");
    final String name = parts[0].trim();
    final List<List<Integer>> values = Arrays.stream(parts[1].split("\n"))
        .filter(Predicate.not(String::isBlank))
        .map(s -> Arrays.stream(s.split(" "))
            .map(Integer::parseInt)
            .toList())
        .toList();

    final List<Range> ranges = values.stream()
        .map(list -> new Range(list.get(0), list.get(1), list.get(2)))
        .toList();

    return new Garden(name, ranges);
  }

  private static List<Integer> getSeeds(List<String> almanac) {
    return Arrays.stream(almanac.getFirst()
            .split(":")[1]
            .trim()
            .split(" "))
        .map(Integer::parseInt)
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
