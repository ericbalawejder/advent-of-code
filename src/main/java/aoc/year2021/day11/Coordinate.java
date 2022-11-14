package aoc.year2021.day11;

import java.util.List;

public record Coordinate(int x, int y) {

  // Each Coordinate has eight neighbors in unit-circle orientation.
  public List<Coordinate> adjacents() {
    return List.of(
        new Coordinate(x + 1, y),
        new Coordinate(x + 1, y + 1),
        new Coordinate(x, y + 1),
        new Coordinate(x - 1, y + 1),
        new Coordinate(x - 1, y),
        new Coordinate(x - 1, y - 1),
        new Coordinate(x, y - 1),
        new Coordinate(x + 1, y - 1)
    );
  }

}
