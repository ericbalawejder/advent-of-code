package aoc.year2021.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TransparentOrigami {

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/year2021/day13/fold-instructions.txt";
        final List<String> data = readData(path);
        final List<Line> instructions = getInstructions(data);
        final Set<Coordinate> coordinates = getCoordinates(data);

        final Set<Coordinate> firstFold = fold(coordinates, instructions.get(0));
        System.out.println(firstFold.size());

        final Set<Coordinate> paper = foldPaper(coordinates, instructions);
        displayPaper(paper);
    }

    static Set<Coordinate> foldPaper(Set<Coordinate> coordinates, List<Line> instructions) {
        Set<Coordinate> paper = new HashSet<>(coordinates);
        for (Line line : instructions) {
            paper = fold(paper, line);
        }
        return Set.copyOf(paper);
    }

    static Set<Coordinate> fold(Set<Coordinate> coordinates, Line line) {
        return line.axis().equals("y") ? foldY(coordinates, line) : foldX(coordinates, line);
    }

    private static Set<Coordinate> foldX(Set<Coordinate> coordinates, Line line) {
        final Set<Coordinate> paper = new HashSet<>(coordinates);
        final int fold = line.value();
        final Set<Coordinate> folded = paper.stream()
                .filter(c -> c.x() > fold)
                .map(c -> new Coordinate(fold * 2 - c.x(), c.y()))
                .collect(Collectors.toUnmodifiableSet());

        final Set<Coordinate> underFold = paper.stream()
                .filter(c -> c.x() < fold)
                .collect(Collectors.toUnmodifiableSet());

        return union(folded, underFold);
    }

    private static Set<Coordinate> foldY(Set<Coordinate> coordinates, Line line) {
        final Set<Coordinate> paper = new HashSet<>(coordinates);
        final int fold = line.value();
        final Set<Coordinate> folded = paper.stream()
                .filter(c -> c.y() > fold)
                .map(c -> new Coordinate(c.x(), fold * 2 - c.y()))
                .collect(Collectors.toUnmodifiableSet());

        final Set<Coordinate> underFold = paper.stream()
                .filter(c -> c.y() < fold)
                .collect(Collectors.toUnmodifiableSet());

        return union(folded, underFold);
    }

    private static void displayPaper(Set<Coordinate> paper) {
        final int xMax = paper.stream()
                .mapToInt(Coordinate::x)
                .max()
                .orElse(0) + 1;

        final int yMax = paper.stream()
                .mapToInt(Coordinate::y)
                .max()
                .orElse(0) + 1;

        final char[][] grid = new char[yMax][xMax];
        IntStream.range(0, yMax).forEach(y -> Arrays.fill(grid[y], ' '));
        paper.forEach(c -> grid[c.y()][c.x()] = '#');
        IntStream.range(0, yMax).forEach(y -> {
            System.out.print(grid[y]);
            System.out.print('\n');
        });
    }

    private static <T> Set<T> union(final Set<T> setA, final Set<T> setB) {
        return Stream.concat(setA.stream(), setB.stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<Coordinate> getCoordinates(List<String> data) {
        return Arrays.stream(data.get(0).split("\n"))
                .map(s -> s.split(","))
                .map(a -> new Coordinate(Integer.parseInt(a[0]), Integer.parseInt(a[1])))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static List<Line> getInstructions(List<String> data) {
        return Arrays.stream(data.get(1)
                        .replace("fold along ", "")
                        .split("\n"))
                .map(s -> s.split("="))
                .map(a -> new Line(a[0], Integer.parseInt(a[1])))
                .toList();
    }

    private static List<String> readData(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n")).toList();
    }

}
