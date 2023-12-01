package aoc.year2021.day17;

import java.util.ArrayList;
import java.util.List;

public record Area(Coordinate topLeft, Coordinate bottomRight) {

  boolean contains(Coordinate coordinate) {
    return topLeft.x() <= coordinate.x() &&
        coordinate.x() <= bottomRight.x() &&
        coordinate.y() <= topLeft.y() &&
        bottomRight.y() <= coordinate.y();
  }

  boolean before(Coordinate coordinate) {
    return (coordinate.x() < topLeft.x() && bottomRight.y() < coordinate.y())
        || (coordinate.x() < bottomRight.x() && topLeft.y() < coordinate.y());
  }

  List<Coordinate> velocityCandidates() {
    final List<Coordinate> velocityCandidates = new ArrayList<>();
    for (int x = 0; x < bottomRight.x() * 2; x++) {
      for (int y = bottomRight.y() * 2; y < -bottomRight().y() * 2; y++) {
        velocityCandidates.add(new Coordinate(x, y));
      }
    }
    return List.copyOf(velocityCandidates);
  }

}
