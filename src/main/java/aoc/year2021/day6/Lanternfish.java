package aoc.year2021.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lanternfish {

    private final static Map<Integer, Integer> FISH_AGES = IntStream.rangeClosed(0, 8)
            .boxed()
            .collect(Collectors.groupingBy(
                    Function.identity(),
                    Collectors.reducing(0, e -> 0, Integer::sum)));

    public static void main(String[] args) throws IOException {
        //final String path = "src/main/java/aoc/year2021/day6/lanternfish-ages.txt";
        final String path = "src/main/java/aoc/year2021/day6/test.txt";
        final List<Integer> agesOfFish = getFish(path);
    }

    static long countLanternfish(List<Integer> agesOfFish, int numberOfDays) {
        final Map<Integer, Integer> fishByNumber = agesOfFish.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.reducing(0, e -> 1, Integer::sum)));

        Map<Integer, Integer> fishCount = Stream.of(FISH_AGES, fishByNumber)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum),
                        Collections::unmodifiableMap));

        for (int i = 0; i < numberOfDays; i++) {
            final int zeros = fishCount.get(0);
            System.out.println("zeros= " + zeros);
            final List<Integer> ages = Stream.concat(
                            nextDay(new ArrayList<>(fishCount.values())).stream(),
                            IntStream.range(0, zeros).map(e -> 0).boxed())
                    .toList();

            System.out.println("ages " + ages);
        }
        System.out.println(fishCount);
        return fishCount.values()
                .stream()
                .reduce(0, Math::addExact);
    }

    private static List<Integer> nextDay(List<Integer> count) {
        return count.stream()
                .map(i -> i - 1)
                .toList();
    }

    private static List<Integer> getFish(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split(","))
                .map(Integer::parseInt)
                .toList();
    }

}
