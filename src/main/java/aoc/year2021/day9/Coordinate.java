package aoc.year2021.day9;

import java.util.List;

public record Coordinate(int x, int y) {

  public List<Coordinate> adjacent() {
    return List.of(
        new Coordinate(x - 1, y),
        new Coordinate(x + 1, y),
        new Coordinate(x, y - 1),
        new Coordinate(x, y + 1)
    );
  }

}
