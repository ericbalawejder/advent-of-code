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
        final Map<Coordinate, Integer> octopusesByEnergy = readOctopusesByEnergy(path);
        System.out.println(generateSteps(octopusesByEnergy, 100));
        System.out.println(findSynchronizedMap(octopusesByEnergy));
    }

    private static int generateSteps(Map<Coordinate, Integer> octopusesByEnergy, int steps) {
        final Map<Coordinate, Integer> energyLevels = new HashMap<>(octopusesByEnergy);
        return IntStream.range(0, steps)
                .map(i -> step(energyLevels))
                .reduce(0, Integer::sum);
    }

    private static int findSynchronizedMap(Map<Coordinate, Integer> octopusesByEnergy) {
        final Map<Coordinate, Integer> energyLevels = new HashMap<>(octopusesByEnergy);
        return (int) IntStream.iterate(0, i -> i + 1)
                .peek(i -> step(energyLevels))
                .takeWhile(i -> !isSynchronized(energyLevels))
                .count() + 1;
    }

    private static int step(Map<Coordinate, Integer> octopusesByEnergy) {
        final Set<Coordinate> flashed = new HashSet<>();
        final Queue<Coordinate> coordinates = new LinkedList<>(octopusesByEnergy.keySet());
        while (!coordinates.isEmpty()) {
            final Coordinate coordinate = coordinates.poll();
            if (!flashed.contains(coordinate)) {
                final Integer energyLevel = octopusesByEnergy.computeIfPresent(coordinate, (k, v) -> v + 1);
                if (energyLevel != null && energyLevel > 9) {
                    flashed.add(coordinate);
                    coordinate.adjacents()
                            .stream()
                            .filter(c -> octopusesByEnergy.containsKey(c) && !flashed.contains(c))
                            .forEach(coordinates::add);
                }
            }
        }
        flashed.forEach(coordinate -> octopusesByEnergy.put(coordinate, 0));
        return flashed.size();
    }

    private static boolean isSynchronized(Map<Coordinate, Integer> octopusesByEnergy) {
        return octopusesByEnergy.values()
                .stream()
                .allMatch(i -> i == 0);
    }

    private static Map<Coordinate, Integer> readOctopusesByEnergy(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            final List<String> lines = stream.toList();
            final Map<Coordinate, Integer> octopusesByEnergy = new LinkedHashMap<>();
            for (int y = 0; y < lines.size(); y++) {
                final String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    final int number = Integer.parseInt(String.valueOf(line.charAt(x)));
                    octopusesByEnergy.put(new Coordinate(x, y), number);
                }
            }
            return Collections.unmodifiableMap(octopusesByEnergy);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

}
