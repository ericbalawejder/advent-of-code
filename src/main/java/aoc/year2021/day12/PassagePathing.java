package aoc.year2021.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class PassagePathing {

    // 226, 3509
    // {WI=[he], DX=[he, pj, fs], RW=[he, pj, zg], start=[DX, pj, RW], sl=[zg],
    // pj=[DX, zg, he, RW, fs], fs=[end, he, DX, pj], he=[DX, fs, pj, RW, WI, zg],
    // zg=[end, sl, pj, RW, he]}
    public static void main(String[] args) {
        final String path = "src/main/java/aoc/year2021/day12/test3.txt";
        final Map<String, List<String>> caves = readCaves(path);
        System.out.println(countPaths(caves, "start", new ArrayList<>(), false));
        System.out.println(countPaths(caves, "start", new ArrayList<>(), true));
        System.out.println(caves);
    }

    static int countPaths(Map<String, List<String>> cavesByPath, String start,
                          List<String> visited, boolean repeat) {
        if (start.equals("end")) {
            return 1;
        }
        int total = 0;
        final List<String> caves = cavesByPath.get(start);
        for (String cave : caves) {
            final List<String> paths = start.toLowerCase().equals(start)
                    ? Stream.concat(visited.stream(), Stream.of(start)).toList()
                    : visited;
            if (visited.contains(cave) && repeat) {
                total += countPaths(cavesByPath, cave, paths, false);
            } else if (!visited.contains(cave)) {
                total += countPaths(cavesByPath, cave, paths, repeat);
            }
        }
        return total;
    }

    private static Map<String, List<String>> readCaves(String path) {
        final Map<String, List<String>> cavesByPath = new HashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            final List<String> lines = stream.toList();
            for (String line : lines) {
                final String[] caves = line.split("-");
                if (caves[0].equals("end") || caves[1].equals("start")) {
                    final List<String> values = cavesByPath.getOrDefault(caves[1], new ArrayList<>());
                    values.add(caves[0]);
                    cavesByPath.put(caves[1], values);
                } else {
                    final List<String> values = cavesByPath.getOrDefault(caves[0], new ArrayList<>());
                    values.add(caves[1]);
                    cavesByPath.put(caves[0], values);
                    if (!caves[0].equals("start") && !caves[1].equals("end")) {
                        final List<String> val1 = cavesByPath.getOrDefault(caves[1], new ArrayList<>());
                        val1.add(caves[0]);
                        cavesByPath.put(caves[1], val1);
                    }
                }
            }
            return Map.copyOf(cavesByPath);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

}
