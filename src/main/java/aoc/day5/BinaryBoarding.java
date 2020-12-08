package aoc.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BinaryBoarding {

    public static void main(String[] args) {
        final SortedSet<Integer> boardingPasses =
                readFromFileMapToTreeSet("src/main/java/aoc/day5/boarding-passes.txt");
        System.out.println(boardingPasses.last());
        System.out.println(findMySeat(boardingPasses));
    }

    static int findMySeat(SortedSet<Integer> boardingPasses) {
        return IntStream.rangeClosed(boardingPasses.first(), boardingPasses.last())
                .filter(n -> !boardingPasses.contains(n))
                .findFirst()
                .orElse(-1);
    }

    private static int encodingToSeatNumber(String seatEncoding) {
        final String binary = seatEncoding
                .replaceAll("[FL]", "0")
                .replaceAll("[BR]", "1");
        return Integer.parseInt(binary, 2);
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
}
