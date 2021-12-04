package aoc.year2021.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Dive {

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/year2021/day2/planned-course.txt";
        final List<Instruction> commands = readFile(path);
        System.out.println(dive(commands));
        System.out.println(diveAgain(commands));
    }

    static int diveAgain(List<Instruction> commands) {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;
        for (Instruction command : commands) {
            switch (command.direction()) {
                case FORWARD -> {
                    horizontalPosition += command.distance();
                    depth += aim * command.distance();
                }
                case DOWN -> aim += command.distance();
                case UP -> aim -= command.distance();
                default -> throw new IllegalArgumentException("Bad command");
            }
        }
        return horizontalPosition * depth;
    }

    static int dive(List<Instruction> commands) {
        int horizontalPosition = 0;
        int depth = 0;
        for (Instruction command : commands) {
            switch (command.direction()) {
                case FORWARD -> horizontalPosition += command.distance();
                case DOWN -> depth += command.distance();
                case UP -> depth -= command.distance();
                default -> throw new IllegalArgumentException("Bad command");
            }
        }
        return horizontalPosition * depth;
    }

    private static List<Instruction> readFile(String path) throws IOException {
        return Files.lines(Paths.get(path))
                .map(s -> s.split(" "))
                .map(a -> new Instruction(
                        Direction.valueOf(a[0].toUpperCase()),
                        Integer.parseInt(a[1])))
                .toList();
    }

}
