package aoc.year2021.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 {

    public static void main(String[] args) {
        final String path = "src/main/java/aoc/year2021/day11/energy-level.txt";
        System.out.println(generateSteps(readOctopuses(path), 100));
        System.out.println(findSynchronizedMap(readOctopuses(path)));
    }

    private static int generateSteps(OctopusMap octopusMap, int steps) {
        return IntStream.range(0, steps)
                .map(i -> octopusMap.step())
                .sum();
    }

    private static int findSynchronizedMap(OctopusMap octopusMap) {
        return (int) IntStream.iterate(0, i -> i + 1)
                .peek(i -> octopusMap.step())
                .takeWhile(i -> !octopusMap.isSynchronized())
                .count() + 1;
    }

    private static OctopusMap readOctopuses(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            final List<String> lines = stream.toList();
            final OctopusMap octopusMap = new OctopusMap();
            for (int y = 0; y < lines.size(); y++) {
                final String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    final int number = Integer.parseInt(String.valueOf(line.charAt(x)));
                    octopusMap.put(new Coordinate(x, y), number);
                }
            }
            return octopusMap;
        } catch (IOException e) {
            e.printStackTrace();
            return new OctopusMap();
        }
    }

}
