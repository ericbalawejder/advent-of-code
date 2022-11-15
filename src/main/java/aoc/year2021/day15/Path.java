package aoc.year2021.day15;

import java.util.Comparator;

public record Path(Coordinate currentCoordinate, int cost) implements Comparable<Path> {

  @Override
  public int compareTo(Path o) {
    return Comparator.comparingInt(Path::cost).compare(this, o);
  }

}
