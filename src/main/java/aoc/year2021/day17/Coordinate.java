package aoc.year2021.day17;

import java.util.Comparator;

public record Coordinate(int x, int y) implements Comparable<Coordinate> {

  public Coordinate slowDown() {
    final int drag = x > 0 ? x - 1 : x < 0 ? x + 1 : 0;
    final int gravity = y - 1;
    return new Coordinate(drag, gravity);
  }

  public Coordinate plus(Coordinate coordinate) {
    return new Coordinate(x + coordinate.x, y + coordinate.y);
  }

  @Override
  public int compareTo(Coordinate coordinate) {
    return Comparator.comparingInt(Coordinate::x)
        .thenComparingInt(Coordinate::y)
        .compare(this, coordinate);
  }

}
