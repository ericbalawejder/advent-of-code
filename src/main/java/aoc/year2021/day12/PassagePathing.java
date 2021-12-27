package aoc.year2021.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PassagePathing {

    public static void main(String[] args) {
        final String path = "src/main/java/aoc/year2021/day12/caves.txt";
        final Map<String, Set<String>> caves = readCaves(path);
        System.out.println(countPaths(caves, "start", new HashSet<>(), false));
        System.out.println(countPaths(caves, "start", new HashSet<>(), true));
    }

    static int countPaths(Map<String, Set<String>> cavesByPath, String start,
                          Set<String> visited, boolean repeat) {
        if (start.equals("end")) {
            return 1;
        }
        int total = 0;
        final Set<String> caves = cavesByPath.get(start);
        for (String cave : caves) {
            final Set<String> paths = start.toLowerCase().equals(start) ?
                    Stream.concat(visited.stream(), Stream.of(start))
                            .collect(Collectors.toUnmodifiableSet())
                    : visited;
            if (visited.contains(cave) && repeat) {
                total += countPaths(cavesByPath, cave, paths, false);
            } else if (!visited.contains(cave)) {
                total += countPaths(cavesByPath, cave, paths, repeat);
            }
        }
        return total;
    }

    private static Map<String, Set<String>> readCaves(String path) {
        final Map<String, Set<String>> cavesByPath = new HashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            final List<String> lines = stream.toList();
            for (String line : lines) {
                final List<String> pair = Arrays.stream(line.split("-")).toList();
                if (pair.get(0).equals("end") || pair.get(1).equals("start")) {
                    final Set<String> v1 = cavesByPath.getOrDefault(pair.get(1), new HashSet<>());
                    v1.add(pair.get(0));
                    cavesByPath.put(pair.get(1), v1);
                } else {
                    if (!pair.get(0).equals("start") && !pair.get(1).equals("end")) {
                        final Set<String> v1 = cavesByPath.getOrDefault(pair.get(1), new HashSet<>());
                        v1.add(pair.get(0));
                        cavesByPath.put(pair.get(1), v1);
                    }
                    final Set<String> v2 = cavesByPath.getOrDefault(pair.get(0), new HashSet<>());
                    v2.add(pair.get(1));
                    cavesByPath.put(pair.get(0), v2);
                }
            }
            return Map.copyOf(cavesByPath);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

}
