package aoc.day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RambunctiousRecitation {

    public static void main(String[] args) {
        final List<Long> startingNumbers =
                readFile("src/main/java/aoc/day15/starting-numbers.txt");
        System.out.println(playMemoryGame(startingNumbers, 2020));
        System.out.println(playMemoryGame(startingNumbers, 30000000));
    }

    static long playMemoryGame(List<Long> startingNumbers, int turnLimit) {
        Map<Long, Long> turnValues = new HashMap<>();
        IntStream.rangeClosed(1, startingNumbers.size() - 1)
                .forEach(i -> turnValues.put(startingNumbers.get(i - 1), (long) i));

        long current = startingNumbers.get(startingNumbers.size() - 1);
        for (long turn = startingNumbers.size() + 1; turn <= turnLimit; turn++) {
            long nextNumber = turnValues.containsKey(current) ? turn - 1 - turnValues.get(current) : 0;
            turnValues.put(current, turn - 1);
            current = nextNumber;
        }
        return current;
    }

    private static List<Long> readFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            final String line = stream.collect(Collectors.joining());
            return Arrays.stream(line.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}