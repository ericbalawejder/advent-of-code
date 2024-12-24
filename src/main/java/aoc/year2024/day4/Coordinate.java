package aoc.year2024.day4;

import java.util.List;

record Coordinate(int x, int y) {

  List<Path> xPaths() {
    return List.of(
        new Path(
            List.of(
                new Coordinate(x + 1, y + 1),
                new Coordinate(x, y),
                new Coordinate(x - 1, y - 1))),

        new Path(
            List.of(
                new Coordinate(x - 1, y + 1),
                new Coordinate(x, y),
                new Coordinate(x + 1, y - 1))));
  }

  List<Path> adjacentPaths() {
    return List.of(
        new Path(
            List.of(
                new Coordinate(x, y),
                new Coordinate(x + 1, y),
                new Coordinate(x + 2, y),
                new Coordinate(x + 3, y))),

        new Path(
            List.of(
                new Coordinate(x, y),
                new Coordinate(x + 1, y + 1),
                new Coordinate(x + 2, y + 2),
                new Coordinate(x + 3, y + 3))),

        new Path(
            List.of(
                new Coordinate(x, y),
                new Coordinate(x, y + 1),
                new Coordinate(x, y + 2),
                new Coordinate(x, y + 3))),

        new Path(
            List.of(
                new Coordinate(x, y),
                new Coordinate(x - 1, y + 1),
                new Coordinate(x - 2, y + 2),
                new Coordinate(x - 3, y + 3))),

        new Path(
            List.of(
                new Coordinate(x, y),
                new Coordinate(x - 1, y),
                new Coordinate(x - 2, y),
                new Coordinate(x - 3, y))),

        new Path(
            List.of(
                new Coordinate(x, y),
                new Coordinate(x - 1, y - 1),
                new Coordinate(x - 2, y - 2),
                new Coordinate(x - 3, y - 3))),

        new Path(
            List.of(
                new Coordinate(x, y),
                new Coordinate(x, y - 1),
                new Coordinate(x, y - 2),
                new Coordinate(x, y - 3))),

        new Path(
            List.of(
                new Coordinate(x, y),
                new Coordinate(x + 1, y - 1),
                new Coordinate(x + 2, y - 2),
                new Coordinate(x + 3, y - 3))));
  }

}
