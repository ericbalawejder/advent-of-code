package aoc.year2021.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 {

    public static void main(String[] args) {
        final String path = "src/main/java/aoc/year2021/day11/energy-level.txt";
        final Map<Coordinate, Integer> octopuses = readOctopuses(path);
        System.out.println(generateSteps(octopuses, 100));
        System.out.println(findSynchronizedMap(octopuses));
    }

    private static int generateSteps(Map<Coordinate, Integer> octopuses, int steps) {
        final Map<Coordinate, Integer> energyLevels = new HashMap<>(octopuses);
        return IntStream.range(0, steps)
                .map(i -> step(energyLevels))
                .sum();
    }

    private static int findSynchronizedMap(Map<Coordinate, Integer> octopuses) {
        final Map<Coordinate, Integer> energyLevels = new HashMap<>(octopuses);
        return (int) IntStream.iterate(0, i -> i + 1)
                .peek(i -> step(energyLevels))
                .takeWhile(i -> !isSynchronized(energyLevels))
                .count() + 1;
    }

    private static int step(Map<Coordinate, Integer> octopuses) {
        final Set<Coordinate> flashers = new HashSet<>();
        final Queue<Coordinate> toIncrease = new LinkedList<>(octopuses.keySet());
        while (!toIncrease.isEmpty()) {
            final Coordinate top = toIncrease.poll();
            if (!flashers.contains(top)) {
                final Integer value = octopuses.compute(top, (k, v) -> v == null ? null : v + 1);
                if (value > 9) {
                    flashers.add(top);
                    top.adjacents()
                            .stream()
                            .filter(c -> octopuses.containsKey(c) && !flashers.contains(c))
                            .forEach(toIncrease::add);
                }
            }
        }
        flashers.forEach(c -> octopuses.put(c, 0));
        return flashers.size();
    }

    private static boolean isSynchronized(Map<Coordinate, Integer> octopuses) {
        return octopuses.values()
                .stream()
                .allMatch(i -> i == 0);
    }

    private static Map<Coordinate, Integer> readOctopuses(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            final List<String> lines = stream.toList();
            final Map<Coordinate, Integer> octopuses = new LinkedHashMap<>();
            for (int y = 0; y < lines.size(); y++) {
                final String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    final int number = Integer.parseInt(String.valueOf(line.charAt(x)));
                    octopuses.put(new Coordinate(x, y), number);
                }
            }
            return Collections.unmodifiableMap(octopuses);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

}
