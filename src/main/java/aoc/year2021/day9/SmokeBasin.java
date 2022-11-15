package aoc.year2021.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class SmokeBasin {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day9/heightmap.txt";
    final Map<Coordinate, Integer> heightMap = createHeightMap(path);
    System.out.println(sumLowPoints(heightMap));
    System.out.println(productOfThreeLargestBasins(heightMap));
  }

  static int sumLowPoints(Map<Coordinate, Integer> heightMap) {
    return heightMap.keySet()
        .stream()
        .filter(c -> isLowPoint(c, heightMap))
        .map(heightMap::get)
        .map(i -> i + 1)
        .reduce(0, Integer::sum);
  }

  static int productOfThreeLargestBasins(Map<Coordinate, Integer> heightMap) {
    return heightMap.keySet()
        .stream()
        .filter(coordinate -> isLowPoint(coordinate, heightMap))
        .map(coordinate -> findBasin(coordinate, heightMap))
        .map(Set::size)
        .sorted(Comparator.reverseOrder())
        .limit(3)
        .reduce(1, Math::multiplyExact);
  }

  private static boolean isLowPoint(Coordinate coordinate, Map<Coordinate, Integer> heightMap) {
    return coordinate.adjacent()
        .stream()
        .filter(heightMap::containsKey)
        .allMatch(adjacent -> heightMap.get(coordinate) < heightMap.get(adjacent));
  }

  private static Set<Coordinate> findBasin(Coordinate lowPoint, Map<Coordinate, Integer> heightMap) {
    final Set<Coordinate> seen = new HashSet<>();
    final Queue<Coordinate> queue = new LinkedList<>();
    final Set<Coordinate> basin = new HashSet<>();
    seen.add(lowPoint);
    queue.add(lowPoint);
    basin.add(lowPoint);
    while (!queue.isEmpty()) {
      Coordinate next = queue.poll();
      next.adjacent().forEach(c -> {
        if (heightMap.containsKey(c) && !seen.contains(c) && heightMap.get(c) < 9) {
          seen.add(c);
          queue.add(c);
          basin.add(c);
        }
      });
    }
    return Set.copyOf(basin);
  }

  private static Map<Coordinate, Integer> createHeightMap(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      final List<String> lines = stream.toList();
      final Map<Coordinate, Integer> heightMap = new LinkedHashMap<>();
      for (int y = 0; y < lines.size(); y++) {
        final String line = lines.get(y);
        for (int x = 0; x < line.length(); x++) {
          final int number = Integer.parseInt(String.valueOf(line.charAt(x)));
          heightMap.put(new Coordinate(x, y), number);
        }
      }
      return Collections.unmodifiableMap(heightMap);
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyMap();
    }
  }

}
