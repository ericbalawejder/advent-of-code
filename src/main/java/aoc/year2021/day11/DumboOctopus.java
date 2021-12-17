package aoc.year2021.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DumboOctopus {

    public static void main(String[] args) {
        final String path = "src/main/java/aoc/year2021/day11/test.txt";
        //final String path = "src/main/java/aoc/year2021/day11/energy-level.txt";
        final Map<Coordinate, Integer> energyLevels = getOctopuses(path);
        System.out.println(energyLevels);
        System.out.println(generateSteps(energyLevels, 10));
    }

    private static Map<Coordinate, Integer> generateSteps(
            Map<Coordinate, Integer> octopusEnergyLevel, int steps) {

        final Map<Coordinate, Integer> grid = new LinkedHashMap<>(octopusEnergyLevel);
        int flashes = 0;
        for (int i = 0; i < steps; i++) {
            grid.replaceAll((k, v) -> v + 1);
            for (Map.Entry<Coordinate, Integer> entry : grid.entrySet()) {
                if (entry.getValue() > 9) {
                    grid.put(entry.getKey(), entry.getValue() % 10);
                    flashes++;
                }
            }
            for (Map.Entry<Coordinate, Integer> entry : grid.entrySet()) {
                if (entry.getValue() == 0) {
                    entry.getKey()
                            .adjacents()
                            .stream()
                            .filter(coord -> grid.containsKey(coord) && grid.get(coord) != 0)
                            .forEach(coord -> grid.replaceAll((k, v) -> v + 1));
                }
            }
        }
        System.out.println(flashes);
        return Collections.unmodifiableMap(grid);
    }

    private static Map<Coordinate, Integer> getOctopuses(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            final List<String> lines = stream.toList();
            final Map<Coordinate, Integer> octopus = new LinkedHashMap<>();
            for (int y = 0; y < lines.size(); y++) {
                final String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    final int number = Integer.parseInt(String.valueOf(line.charAt(x)));
                    octopus.put(new Coordinate(x, y), number);
                }
            }
            return Collections.unmodifiableMap(octopus);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

}
