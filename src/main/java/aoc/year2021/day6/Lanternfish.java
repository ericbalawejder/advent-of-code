package aoc.year2021.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lanternfish {

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/year2021/day6/lanternfish-ages.txt";
        final List<Integer> agesOfFish = getFish(path);
        System.out.println(countFish(agesOfFish, 80));
    }

    static long countFish(List<Integer> agesOfFish, int numberOfDays) {
        List<Integer> fish = new ArrayList<>(agesOfFish);
        for (int day = 0; day < numberOfDays; day++) {
            final int zeros = (int) fish.stream()
                    .filter(i -> i == 0)
                    .count();

            final List<Integer> resetAge = fish.stream()
                    .map(Lanternfish::reproduce)
                    .toList();

            final List<Integer> addNewFish = Stream.concat(
                            resetAge.stream(),
                            IntStream.range(0, zeros).mapToObj(i -> 9))
                    .toList();

            fish = addNewFish.stream()
                    .map(i -> i - 1)
                    .toList();
        }
        return fish.size();
    }

    private static int reproduce(int age) {
        return age == 0 ? 7 : age;
    }

    private static List<Integer> getFish(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split(","))
                .map(Integer::parseInt)
                .toList();
    }

}
