package aoc.utils;

import java.util.Arrays;

public class CartesianCoordinate {

    private final int[][] grid;

    public CartesianCoordinate(int xLength, int yLength) {
        this.grid = new int[xLength][yLength];
    }

    public CartesianCoordinate(CartesianCoordinate system) {
        this.grid = system.getGrid();
    }

    public int[][] getGrid() {
        return grid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartesianCoordinate that = (CartesianCoordinate) o;
        return Arrays.deepEquals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int[] ints : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                stringBuilder.append(ints[j]);
                if (j < grid.length - 1) {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
