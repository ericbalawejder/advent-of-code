package aoc.year2024.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GuardGallivant {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2024/day6/map.txt";
    final Map<Coordinate, Character> labLayout = readLabLayout(path);
    final Guard guard = findGuard(labLayout, Direction.NORTH);

    final Set<Coordinate> distinctPath = navigate(labLayout, guard);
    final int count = distinctPath.size();
    System.out.println(count);

    final int loopCount = countAllLoops(labLayout, distinctPath, guard);
    System.out.println(loopCount);
  }

  static int countAllLoops(Map<Coordinate, Character> labLayout, Set<Coordinate> guardPath, Guard start) {
    final Guard guard = new Guard(start);
    final Map<Coordinate, Character> currentLayout = labLayout.entrySet()
        .stream()
        .map(entry -> entry.getKey().equals(guard.getCoordinate()) ? Map.entry(entry.getKey(), '.') : entry)
        .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

    final Map<Coordinate, Character> obstaclePath = labLayout.entrySet()
        .stream()
        .filter(entry -> guardPath.contains(entry.getKey()))
        .filter(entry -> !entry.getKey().equals(guard.getCoordinate()))
        .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

    return obstaclePath.keySet()
        .stream()
        .map(coordinate -> runSimulation(currentLayout, coordinate, guard))
        .reduce(0, Integer::sum);
  }

  static Set<Coordinate> navigate(Map<Coordinate, Character> labLayout, Guard start) {
    final Guard guard = new Guard(start);
    final Map<Coordinate, Character> currentLayout = labLayout.entrySet()
        .stream()
        .map(entry -> entry.getKey().equals(guard.getCoordinate()) ? Map.entry(entry.getKey(), '.') : entry)
        .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

    final Set<Coordinate> visited = Stream.of(guard.getCoordinate())
        .collect(Collectors.toSet());

    while (guard.isOn()) {
      if (guard.lookAhead(currentLayout) == null) {
        visited.add(guard.getCoordinate());
        guard.setOn(false);
      } else if (guard.lookAhead(currentLayout).equals('.')) {
        guard.walk();
        visited.add(guard.getCoordinate());
      } else if (guard.lookAhead(currentLayout).equals('#')) {
        guard.turnRight();
      }
    }
    return Set.copyOf(visited);
  }

  private static int runSimulation(Map<Coordinate, Character> currentLayout, Coordinate coordinate, Guard start) {
    final Map<Coordinate, Character> obstacleCourse = currentLayout.entrySet()
        .stream()
        .map(entry -> entry.getKey().equals(coordinate) ? Map.entry(entry.getKey(), '#') : entry)
        .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

    final Guard guard = new Guard(start);
    final Traversal guardTraversal = new Traversal(guard.getCoordinate(), guard.getDirection());
    final Set<Traversal> visited = Stream.of(guardTraversal)
        .collect(Collectors.toSet());

    int loopCount = 0;
    while (guard.isOn()) {
      if (guard.lookAhead(obstacleCourse) == null) {
        guard.setOn(false);
      } else if (guard.lookAhead(obstacleCourse).equals('.')) {
        guard.walk();
        if (visited.contains(new Traversal(guard.getCoordinate(), guard.getDirection()))) {
          loopCount++;
          guard.setOn(false);
        } else {
          visited.add(new Traversal(guard.getCoordinate(), guard.getDirection()));
        }
      } else if (guard.lookAhead(obstacleCourse).equals('#')) {
        guard.turnRight();
      }
    }
    return loopCount;
  }

  private static Guard findGuard(Map<Coordinate, Character> labLayout, Direction direction) {
    final Coordinate start = labLayout.entrySet()
        .stream()
        .filter(entry -> entry.getValue().equals('^'))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("guard not found"));

    return new Guard(start, direction, true);
  }

  private static Map<Coordinate, Character> readLabLayout(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      final List<String> lines = stream.toList();
      final Map<Coordinate, Character> lab = new LinkedHashMap<>();
      for (int y = lines.size() - 1; y >= 0; y--) {
        final String line = lines.get(y);
        for (int x = 0; x < line.length(); x++) {
          lab.put(new Coordinate(x, lines.size() - 1 - y), line.charAt(x));
        }
      }
      return Collections.unmodifiableMap(lab);
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyMap();
    }
  }

  @SuppressWarnings("unused")
  private static Map<Coordinate, Character> readLabLayoutReverse(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      final AtomicInteger lineCount = new AtomicInteger();
      final Map<Coordinate, Character> lab = new LinkedHashMap<>();
      stream.forEach(line -> {
        for (int y = lineCount.getAndIncrement(); y < line.length(); y++) {
          for (int x = 0; x < line.length(); x++) {
            lab.put(new Coordinate(x, y), line.charAt(x));
          }
        }
      });
      return Collections.unmodifiableMap(lab);
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyMap();
    }
  }

}
