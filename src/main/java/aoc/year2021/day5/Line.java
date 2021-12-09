package aoc.year2021.day5;

// public record Line(Point a, Point b) {
public record Line(int x1, int y1, int x2, int y2) {

    public boolean isVerticalOrHorizontal() {
        return x1 == x2 || y1 == y2;
    }

    public boolean isVerticalOrHorizontalOrDiagonal() {
        return isVerticalOrHorizontal() || isDiagonal();
    }

    public boolean isDiagonal() {
        return Math.abs(x1 - x2) == Math.abs(y1 - y2);
    }

    public int length() {
        final int manhattanDistance = Math.abs(x1 - x2) + Math.abs(y1 - y2);
        return isDiagonal() ? manhattanDistance / 2 + 1 : manhattanDistance + 1;
    }

}
