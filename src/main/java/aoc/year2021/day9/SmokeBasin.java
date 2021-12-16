package aoc.year2021.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
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
                .distinct()
                .sorted()
                .limit(3)
                .map(Basin::size)
                .reduce(1, Math::multiplyExact);
    }

    private static boolean isLowPoint(Coordinate coordinate, Map<Coordinate, Integer> heightMap) {
        return coordinate.adjacent()
                .stream()
                .filter(heightMap::containsKey)
                .allMatch(adjacent -> heightMap.get(coordinate) < heightMap.get(adjacent));
    }

    private static Basin findBasin(Coordinate lowPoint, Map<Coordinate, Integer> heightMap) {
        final Set<Coordinate> seen = new HashSet<>();
        final Queue<Coordinate> queue = new LinkedList<>();
        final Basin basin = new Basin();
        seen.add(lowPoint);
        queue.add(lowPoint);
        basin.add(lowPoint);
        while (!queue.isEmpty()) {
            Coordinate next = queue.poll();
            next.adjacent().forEach(coordinate -> {
                if (heightMap.containsKey(coordinate) &&
                        !seen.contains(coordinate) &&
                        heightMap.get(coordinate) < 9) {
                    seen.add(coordinate);
                    queue.add(coordinate);
                    basin.add(coordinate);
                }
            });
        }
        // TODO: make immutable collection
        return basin;
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
