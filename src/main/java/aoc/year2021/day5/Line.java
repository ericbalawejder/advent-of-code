package aoc.year2021.day5;

public record Line(Point a, Point b) {

  public boolean isVerticalOrHorizontalOrDiagonal() {
    return isVerticalOrHorizontal() || isDiagonal();
  }

  public boolean isVerticalOrHorizontal() {
    return a().x() == b().x() || a().y() == b().y();
  }

  public boolean isDiagonal() {
    return Math.abs(a().x() - b().x()) == Math.abs(a().y() - b().y());
  }

  public int length() {
    final int manhattanDistance = Math.abs(a().x() - b().x()) + Math.abs(a().y() - b().y());

    return isDiagonal() ? manhattanDistance / 2 + 1 : manhattanDistance + 1;
  }

}
