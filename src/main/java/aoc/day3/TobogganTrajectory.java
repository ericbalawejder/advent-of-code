package aoc.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TobogganTrajectory {

    public static void main(String[] args) {
        TobogganTrajectory toboggan = new TobogganTrajectory();
        int[][] grid = toboggan.readFromFile("src/main/java/aoc/day3/tree-grid.txt");
        //System.out.println(Arrays.deepToString(grid));
        System.out.println(toboggan.countTrees(grid, 3, 1));
        System.out.println(toboggan.productOfTobogganTrajectories(
                grid, Arrays.asList(Arrays.asList(1, 1),
                                    Arrays.asList(3, 1),
                                    Arrays.asList(5, 1),
                                    Arrays.asList(7,1),
                                    Arrays.asList(1, 2)))
        );
    }

    // Part 1
    long countTrees(int[][] grid, int x, int y) {
        return IntStream.iterate(0, i -> i < grid.length, i -> i += y)
                .filter(i -> grid[i][i * x % grid[0].length] == (int) '#')
                .count();
    }

    // Part 2
    long productOfTobogganTrajectories(int[][] grid, List<List<Integer>> slopes) {
        return slopes.stream()
                .map(slope -> countTrees(grid, slope.get(0), slope.get(1)))
                .reduce(1L, (a, b) -> a * b);
    }

    private int[][] readFromFile(String path) {
        int[][] grid = new int[0][];

        // Using try-with-resources so the stream closes automatically
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            grid = stream.map(s -> s.chars().toArray())
                    .toArray(int[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.stream(grid)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }
}
