package aoc.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdapterArray {

    private static final int CHARGING_OUTLET_RATING = 0;

    public static void main(String[] args) {
        final SortedSet<Integer> outputJoltage =
                readFileToTreeSet("src/main/java/aoc/day10/output-joltage.txt");
        System.out.println(productOfJoltDifference(outputJoltage));
        System.out.println(findAllCombinationsOfAdapters(outputJoltage));
    }

    private static int productOfJoltDifference(SortedSet<Integer> sortedAdapters) {
        int currentJolt = CHARGING_OUTLET_RATING;
        int oneJolt = 0;
        int threeJolt = 0;
        for (Integer jolt : sortedAdapters) {
            int difference = jolt - currentJolt;
            if (difference == 1) {
                oneJolt++;
            } else if (difference == 3) {
                threeJolt++;
            }
            currentJolt = jolt;
        }
        return oneJolt * threeJolt;
    }

    private static long findAllCombinationsOfAdapters(SortedSet<Integer> sortedAdapters) {
        final Map<Integer, Long> combinations = new HashMap<>();
        combinations.put(0, 1L);

        for (Integer adapter : sortedAdapters) {
            long arrangements = 0;
            for (int difference = 1; difference <= 3; difference++) {
                int previous = adapter - difference;
                if (combinations.containsKey(previous)) {
                    arrangements += combinations.get(previous);
                }
            }
            combinations.put(adapter, arrangements);
        }
        return combinations.get(sortedAdapters.last());
    }

    private static SortedSet<Integer> readFileToTreeSet(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            SortedSet<Integer> adapters = stream.map(Integer::parseInt)
                    .collect(Collectors.toCollection(TreeSet::new));
            adapters.add(adapters.last() + 3);
            return new TreeSet<>(adapters);
        } catch (IOException e) {
            e.printStackTrace();
            return new TreeSet<>();
        }
    }
}
