package aoc.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BinaryBoarding {

    public static void main(String[] args) {
        final List<String> boardingPasses =
                readFromFile("src/main/java/aoc/day5/boarding-passes.txt");
        System.out.println(highestBoardingPassSeatId(boardingPasses));
        System.out.println(findMySeatId(seatIds(boardingPasses)));
    }

    // Part 1
    static int highestBoardingPassSeatId(List<String> boardingPasses) {
        return seatIds(boardingPasses).stream()
                .reduce(Integer::max)
                .orElse(-1);
    }

    // Part 2
    static int findMySeatId(Set<Integer> seatIds) {
        return seatIds.stream()
                .filter(id -> seatIds.contains(id) &&
                            !seatIds.contains(id + 1) &&
                            seatIds.contains(id + 2))
                .findAny()
                .orElse(-1) + 1;
    }

    private static Set<Integer> seatIds(List<String> boardingPasses) {
        return boardingPasses.stream()
                .map(BinaryBoarding::calculateSeatId)
                .collect(Collectors.toUnmodifiableSet());
    }

    private static int calculateSeatId(String seatEncoding) {
        int rowLow = 0;
        int rowHigh = 127;
        int columnLow = 0;
        int columnHigh = 7;
        for (char c : seatEncoding.toCharArray()) {
            switch (c) {
                case 'F':
                    rowHigh -= (rowHigh - rowLow + 1) / 2;
                    break;
                case 'B':
                    rowLow += (rowHigh - rowLow + 1) / 2;
                    break;
                case 'L':
                    columnHigh -= (columnHigh - columnLow + 1) / 2;
                    break;
                case 'R':
                    columnLow += (columnHigh - columnLow + 1) / 2;
                    break;
                default:
                    throw new IllegalArgumentException("Bad encoding");
            }
        }
        return rowHigh * 8 + columnHigh;
    }

    private static List<String> readFromFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
