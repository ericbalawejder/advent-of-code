package aoc.year2021.day15;

import java.util.Comparator;
import java.util.List;

public record Coordinate(int x, int y) implements Comparable<Coordinate> {

  public List<Coordinate> adjacentCoordinates() {
    return List.of(
        new Coordinate(x - 1, y),
        new Coordinate(x + 1, y),
        new Coordinate(x, y - 1),
        new Coordinate(x, y + 1)
    );
  }

  @Override
  public int compareTo(Coordinate o) {
    return Comparator.comparingInt(Coordinate::x)
        .thenComparingInt(Coordinate::y)
        .compare(this, o);
  }

}