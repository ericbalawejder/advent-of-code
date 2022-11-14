package aoc.year2021.day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Chiton {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day15/risk-levels.txt";
    final SortedMap<Coordinate, Integer> riskLevels = createRiskLevelCoordinates(path);
    System.out.println(findLowestCostPath(riskLevels));

    final SortedMap<Coordinate, Integer> fullCaveRiskLevels = createFullCaveMap(new TreeMap<>(riskLevels));
    System.out.println(findLowestCostPath(fullCaveRiskLevels));
  }

  public static int findLowestCostPath(SortedMap<Coordinate, Integer> grid) {
    final Coordinate endCoordinate = grid.lastKey();
    final Queue<Path> route = new PriorityQueue<>();
    final Set<Coordinate> visited = new HashSet<>();
    route.add(new Path(new Coordinate(0, 0), 0));
    while (!route.isEmpty() && !route.peek().currentCoordinate().equals(endCoordinate)) {
      final Path currentPath = route.poll();
      currentPath.currentCoordinate()
          .adjacentCoordinates()
          .stream()
          .filter(grid::containsKey)
          .filter(coordinate -> !visited.contains(coordinate))
          .map(coordinate -> new Path(coordinate, currentPath.cost() + grid.get(coordinate)))
          .forEach(path -> {
            visited.add(path.currentCoordinate());
            route.add(path);
          });
    }
    return route.element().cost();
  }

  private static SortedMap<Coordinate, Integer> createFullCaveMap(SortedMap<Coordinate, Integer> grid) {
    final SortedMap<Coordinate, Integer> fullCaveMap = new TreeMap<>(grid);
    int maxX = fullCaveMap.lastKey().x();
    int maxY = fullCaveMap.lastKey().y();

    for (int x = maxX + 1; x < 5 * (maxX + 1); x++) {
      for (int y = 0; y <= maxY; y++) {
        fullCaveMap.put(new Coordinate(x, y), rollOverRiskLevel(fullCaveMap.get(new Coordinate(x - maxX - 1, y)) + 1));
      }
    }
    maxX = fullCaveMap.lastKey().x();

    for (int y = maxY + 1; y < 5 * (maxY + 1); y++) {
      for (int x = 0; x <= maxX; x++) {
        fullCaveMap.put(new Coordinate(x, y), rollOverRiskLevel(fullCaveMap.get(new Coordinate(x, y - maxY - 1)) + 1));
      }
    }
    return Collections.unmodifiableSortedMap(fullCaveMap);
  }

  private static int rollOverRiskLevel(int n) {
    return n <= 9 ? n : Math.floorMod(n, 9);
  }

  private static SortedMap<Coordinate, Integer> createRiskLevelCoordinates(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      final List<String> lines = stream.toList();
      final SortedMap<Coordinate, Integer> grid = new TreeMap<>();
      for (int y = 0; y < lines.size(); y++) {
        final String line = lines.get(y);
        for (int x = 0; x < line.length(); x++) {
          grid.put(new Coordinate(x, y), Integer.parseInt(line.substring(x, x + 1)));
        }
      }
      return Collections.unmodifiableSortedMap(grid);
    } catch (IOException e) {
      e.printStackTrace();
      return new TreeMap<>();
    }
  }

}
