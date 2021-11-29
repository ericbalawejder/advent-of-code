package aoc.year2020.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HandheldHaltingPart1 {

    public static void main(String[] args) {
        List<String> instructions = readFromFile("src/main/java/aoc/year2020/day8/boot-code.txt");
        System.out.println(parseInstruction(instructions));
    }

    static long parseInstruction(List<String> instructions) {
        final Set<Integer> visited = new HashSet<>(instructions.size());
        long accumulator = 0;
        int position = 0;

        while (!visited.contains(position) && position < instructions.size()) {

            visited.add(position);
            String instruction = instructions.get(position);
            String operation = instruction.substring(0, 3);
            String argument = instruction.substring(4);

            switch (operation) {
                case "acc" -> {
                    accumulator += Integer.parseInt(argument);
                    position++;
                }
                case "jmp" -> position += Integer.parseInt(argument);
                case "nop" -> position++;
                default -> throw new IllegalArgumentException("Bad instructions");
            }
        }
        return accumulator;
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
