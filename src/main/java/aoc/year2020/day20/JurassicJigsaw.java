package aoc.year2020.day20;

import java.util.Arrays;

public class JurassicJigsaw {

    /**
     * Brute force rotate matrices to match sides?
     * ¯\_(ツ)_/¯
     */
    public static void main(String[] args) {

        int[][] grid = new int[][]{
                {6, 4, 3, 7},
                {2, 1, 8, 5},
                {9, 0, 2, 1}
        };

        print(grid);
        System.out.println();
        print(rotate(grid, 270));
    }

    static int[][] rotate(int[][] matrix, int degrees) {
        if (degrees % 360 == 0) {
            return copyOf(matrix);
        } else {
            int[][] rotatedMatrix = reverseRows(transpose(matrix));

            for (int i = 1; i < rotations(degrees); i++) {
                rotatedMatrix = reverseRows(transpose(rotatedMatrix));
            }
            return copyOf(rotatedMatrix);
        }
    }

    private static int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int[][] transpose = new int[columns][rows];

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                transpose[i][j] = matrix[j][i];
            }
        }
        return transpose;
    }

    private static int[][] reverseRows(int[][] matrix) {
        int[][] reversed = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int temp = matrix[i][j];
                reversed[i][j] = matrix[i][matrix[i].length - j - 1];
                reversed[i][reversed[i].length - j - 1] = temp;
            }
        }
        return reversed;
    }

    private static int rotations(int degrees) {
        if (degrees % 90 != 0) {
            throw new IllegalArgumentException("Input +- 90 degree increments");
        }
        return Math.floorMod(-degrees, 360) / 90;
    }

    private static int[][] copyOf(int[][] matrix) {
        return Arrays.stream(matrix)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    private static void print(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

}
