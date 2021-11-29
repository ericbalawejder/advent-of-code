package aoc.year2020.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.OptionalInt;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BinaryBoarding {

    public static void main(String[] args) {
        final SortedSet<Integer> boardingPasses =
                readFromFileMapToTreeSet("src/main/java/aoc/year2020/day5/boarding-passes.txt");
        System.out.println(highestSeatNumber(boardingPasses));
        System.out.println(findMySeat(boardingPasses));
    }

    static OptionalInt findMySeat(SortedSet<Integer> boardingPasses) {
        return IntStream.rangeClosed(boardingPasses.first(), boardingPasses.last())
                .filter(n -> !boardingPasses.contains(n))
                .findFirst();
    }

    static Integer highestSeatNumber(SortedSet<Integer> boardingPasses) {
        return boardingPasses.last();
    }

    private static SortedSet<Integer> readFromFileMapToTreeSet(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(BinaryBoarding::encodingToSeatNumber)
                    .collect(Collectors.toCollection(TreeSet::new));
        } catch (IOException e) {
            e.printStackTrace();
            return new TreeSet<>();
        }
    }

    private static int encodingToSeatNumber(String seatEncoding) {
        final String toBinary = seatEncoding
                .replaceAll("[FL]", "0")
                .replaceAll("[BR]", "1");
        return Integer.parseInt(toBinary, 2);
    }
}
