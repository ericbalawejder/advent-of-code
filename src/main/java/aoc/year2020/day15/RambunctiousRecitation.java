package aoc.year2020.day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RambunctiousRecitation {

    public static void main(String[] args) throws IOException {
        final List<Integer> startingNumbers =
                readFile("src/main/java/aoc/year2020/day15/starting-numbers.txt");
        System.out.println(playMemoryGame(startingNumbers, 2020));
        System.out.println(playMemoryGame(startingNumbers, 30_000_000));
    }

    static int playMemoryGame(List<Integer> startingNumbers, int turnLimit) {
        final Map<Integer, Integer> turnValues = IntStream.range(0, startingNumbers.size() - 1)
                .map(startingNumbers::get)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), v -> startingNumbers.indexOf(v) + 1));

        int current = startingNumbers.get(startingNumbers.size() - 1);
        for (int turn = startingNumbers.size() + 1; turn <= turnLimit; turn++) {
            int nextNumber = turnValues.containsKey(current) ? turn - 1 - turnValues.get(current) : 0;
            turnValues.put(current, turn - 1);
            current = nextNumber;
        }
        return current;
    }

    private static List<Integer> readFile(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split(","))
                .map(Integer::parseInt)
                .toList();
    }

}
