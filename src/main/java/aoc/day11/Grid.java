package aoc.day11;

import java.util.Arrays;

public class Grid {

    private static final int[][] OFFSETS = new int[][]{{-1, -1}, {-1, 0},
                                                        {-1, 1}, {0, -1},
                                                        {0, 1}, {1, -1},
                                                        {1, 0}, {1, 1}
    };

    private final Status[][] grid;

    public Grid(int rows, int cols) {
        this.grid = new Status[rows][cols];
    }

    public Grid getNextSeatingLayout() {
        final Grid next = new Grid(this.grid.length, this.grid[0].length);
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                if (this.grid[i][j] == Status.EMPTY && countAdjacent(i, j) == 0) {
                    next.setStatus(i, j, Status.OCCUPIED);
                } else if (this.grid[i][j] == Status.OCCUPIED && countAdjacent(i, j) >= 4) {
                    next.setStatus(i, j, Status.EMPTY);
                } else {
                    next.setStatus(i, j, this.grid[i][j]);
                }
            }
        }
        return next;
    }

    public Grid getNextSeatingLayoutVisibility() {
        final Grid next = new Grid(this.grid.length, this.grid[0].length);
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                if (this.grid[i][j] == Status.EMPTY && countVisible(i, j) == 0) {
                    next.setStatus(i, j, Status.OCCUPIED);
                } else if (this.grid[i][j] == Status.OCCUPIED && countVisible(i, j) >= 5) {
                    next.setStatus(i, j, Status.EMPTY);
                } else {
                    next.setStatus(i, j, this.grid[i][j]);
                }
            }
        }
        return next;
    }

    public int countSeats(Status status) {
        int count = 0;
        for (Status[] row : this.grid) {
            for (Status seat : row) {
                count += seat == status ? 1 : 0;
            }
        }
        return count;
    }

    public void setStatus(int row, int col, Status status) {
        this.grid[row][col] = status;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(grid);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Grid)) {
            return false;
        }

        final Grid obj2 = (Grid) obj;
        if (this.grid.length != obj2.grid.length || this.grid[0].length != obj2.grid[0].length) {
            return false;
        }

        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                if (this.grid[i][j] != obj2.grid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Grid{" +
                " grid= " + Arrays.deepToString((grid)) +
                '}';
    }

    private int countAdjacent(int row, int col) {
        int count = 0;
        for (int[] offset : OFFSETS) {
            final int row2 = row + offset[0];
            final int col2 = col + offset[1];
            if (row2 < 0 || row2 >= this.grid.length || col2 < 0 || col2 >= this.grid[row2].length) {
                continue;
            }
            if (this.grid[row2][col2] == Status.OCCUPIED) {
                count++;
            }
        }
        return count;
    }

    private int countVisible(int row, int col) {
        int count = 0;
        for (int[] offset : OFFSETS) {
            int row2 = row;
            int col2 = col;
            while (true) {
                row2 += offset[0];
                col2 += offset[1];
                if (row2 < 0 || row2 >= this.grid.length || col2 < 0 || col2 >= this.grid[row2].length) {
                    break;
                }
                if (this.grid[row2][col2] == Status.EMPTY) {
                    break;
                }
                if (this.grid[row2][col2] == Status.OCCUPIED) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

}
