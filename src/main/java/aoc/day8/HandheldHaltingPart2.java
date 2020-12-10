package aoc.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HandheldHaltingPart2 {

    private static long ACCUMULATOR = 0;

    public static void main(String[] args) {
        List<String> instructions = readFromFile("src/main/java/aoc/day8/boot-code.txt");
        System.out.println(mutateInstructions(instructions));
    }

    static long mutateInstructions(List<String> instructions) {
        boolean endOfFile;

        for (int i = 0; i < instructions.size(); i++) {
            final List<String> modifiedInstructions = new ArrayList<>(instructions);
            String instruction = modifiedInstructions.get(i);
            if (instruction.startsWith("nop")) {
                modifiedInstructions.set(i, instruction.replace("nop", "jmp"));
                endOfFile = parseInstruction(modifiedInstructions);
                if (endOfFile) {
                    return ACCUMULATOR;
                }
            } else if (instruction.startsWith("jmp")) {
                modifiedInstructions.set(i, instruction.replace("jmp", "nop"));
                endOfFile = parseInstruction(modifiedInstructions);
                if (endOfFile) {
                    return ACCUMULATOR;
                }
            }
        }
        return 0;
    }

    static boolean parseInstruction(List<String> instructions) {
        final Set<Integer> visited = new HashSet<>(instructions.size());
        long accumulator = 0;
        int position = 0;

        while (!visited.contains(position) && position < instructions.size()) {

            visited.add(position);
            String instruction = instructions.get(position);
            String operation = instruction.substring(0, 3);
            String argument = instruction.substring(4);

            switch (operation) {
                case "acc":
                    accumulator += Integer.parseInt(argument);
                    position++;
                    break;
                case "jmp":
                    position += Integer.parseInt(argument);
                    break;
                case "nop":
                    position++;
                    break;
                default:
                    throw new IllegalArgumentException("Bad instructions");
            }
        }
        ACCUMULATOR = accumulator;
        return position == instructions.size();
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
