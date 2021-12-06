package aoc.year2021.day4;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GameCard {

    private final int[][] grid;

    public GameCard(String stringToParse) {
        this.grid = createGrid(stringToParse, "\n", " ");
    }

    public int sumUnmarkedCardNumbers() {
        return IntStream.range(0, grid.length)
                .flatMap(i -> Arrays.stream(grid[i]))
                .filter(e -> e != -1)
                .sum();
    }

    public void markCardNumber(int number, int replace) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == number) {
                    grid[i][j] = replace;
                    return;
                }
            }
        }
    }

    public int[][] transpose(int[][] matrix) {
        final int rows = matrix.length;
        final int columns = matrix[0].length;
        final int[][] transpose = new int[columns][rows];

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                transpose[i][j] = matrix[j][i];
            }
        }
        return copyOf(transpose);
    }

    public int[][] getGrid() {
        return grid.clone();
    }

    @Override
    public String toString() {
        return Arrays.deepToString(grid);
    }

    private int[][] copyOf(int[][] grid) {
        return Arrays.stream(grid)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    private int[][] createGrid(String input, String lineDelimiter, String itemDelimiter) {
        final String[] lines = input.split(lineDelimiter);
        final int[][] result = new int[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            result[i] = Arrays.stream(lines[i].split(itemDelimiter))
                    .map(String::trim)
                    .filter(e -> !e.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return result.clone();
    }

    private void print(int[][] grid) {
        for (int[] ints : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

}
