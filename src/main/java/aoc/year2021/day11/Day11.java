package aoc.year2021.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 {

    public static void main(String[] args) {
        final String path = "src/main/java/aoc/year2021/day11/test.txt";
        //final String path = "src/main/java/aoc/year2021/day11/energy-level.txt";
        final OctopusMap octopusMap = readOctopuses(path);
        System.out.println(firstPart(octopusMap, 100));
        System.out.println(secondPart(octopusMap));
    }

    private static int firstPart(OctopusMap oMap, int steps) {
        return IntStream.range(0, steps)
                .map(i -> oMap.step())
                .sum();
    }

    private static int secondPart(OctopusMap oMap) {
        return (int) IntStream.iterate(0, i -> i + 1)
                .peek(i -> oMap.step())
                .takeWhile(i -> !oMap.isSyncron())
                .count() + 1;
    }

    private static OctopusMap readOctopuses(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            final List<String> lines = stream.toList();
            final OctopusMap oMap = new OctopusMap();
            for (int y = 0; y < lines.size(); y++) {
                final String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    final int number = Integer.parseInt(String.valueOf(line.charAt(x)));
                    oMap.put(new Coordinate(x, y), number);
                }
            }
            return oMap;
        } catch (IOException e) {
            e.printStackTrace();
            return new OctopusMap();
        }
    }

}
