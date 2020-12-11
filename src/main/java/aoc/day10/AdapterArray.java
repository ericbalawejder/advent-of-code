package aoc.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdapterArray {

    private static final int CHARGING_OUTLET_RATING = 0;

    public static void main(String[] args) {
        final List<Integer> outputJoltage =
                readFileToList("src/main/java/aoc/day10/output-joltage.txt");
        System.out.println(productOfJoltDifference(outputJoltage));
        System.out.println(combinationsOfPossibleAdapters(outputJoltage));
    }

    static long combinationsOfPossibleAdapters(List<Integer> outputJoltage) {
        List<Integer> output = new ArrayList<>(outputJoltage);
        output.addAll(Arrays.asList(CHARGING_OUTLET_RATING,
                joltageAdapterRating(outputJoltage)));

        final List<Integer> sortedAdapters = sort(output);

        final Map<Integer, List<Integer>> possibleConnections =
                possibleConnections(sortedAdapters);

        final Map<Integer, Long> routes = new HashMap<>();
        routes.put(0, 1L);

        long arrangements = 0;
        for (int i = 1; i < sortedAdapters.size(); i++) {
            arrangements = 0;
            for (int adapter : possibleConnections.get(sortedAdapters.get(i))) {
                arrangements += routes.get(adapter);
            }
            routes.put(sortedAdapters.get(i), arrangements);
        }
        return arrangements;
    }

    static Map<Integer, List<Integer>> possibleConnections(List<Integer> sortedAdapters) {
        final Map<Integer, List<Integer>> possibleConnections = new HashMap<>();

        for (int i = sortedAdapters.size() - 1; i >=0; i--) {
            int next = i - 1;
            final List<Integer> adapterPaths = new ArrayList<>();
            while (next >= 0 && sortedAdapters.get(i) <= sortedAdapters.get(next) + 3) {
                adapterPaths.add(sortedAdapters.get(next));
                next--;
            }
            possibleConnections.put(sortedAdapters.get(i), adapterPaths);
        }
        return Map.copyOf(possibleConnections);
    }

    static int productOfJoltDifference(List<Integer> outputJoltage) {
        List<Integer> output = new ArrayList<>(outputJoltage);
        output.addAll(Arrays.asList(CHARGING_OUTLET_RATING,
                joltageAdapterRating(outputJoltage)));

        final List<Integer> sortedAdapters = sort(output);

        final Map<Integer, Integer> ratingCount = new HashMap<>();
        ratingCount.put(1, 0);
        ratingCount.put(3, 0);

        for (int i = 1; i < sortedAdapters.size(); i++) {
            int difference = sortedAdapters.get(i) - sortedAdapters.get(i - 1);
            if (difference <= 3) {
                if (difference == 1) {
                    ratingCount.put(difference, ratingCount.get(difference) +  1);
                } else if (difference == 3) {
                    ratingCount.put(difference, ratingCount.get(difference) +  1);
                }
            } else {
                break;
            }
        }
        return ratingCount.get(1) * ratingCount.get(3);
    }

    private static int joltageAdapterRating(List<Integer> outputJoltage) {
        return outputJoltage.stream()
                .max(Integer::compareTo)
                .orElse(0) + 3;
    }

    private static List<Integer> sort(List<Integer> list) {
        return list.stream()
                .sorted()
                .collect(Collectors.toUnmodifiableList());
    }

    private static List<Integer> readFileToList(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
