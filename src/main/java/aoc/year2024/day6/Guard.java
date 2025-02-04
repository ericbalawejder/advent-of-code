package aoc.year2024.day6;

import java.util.Map;
import java.util.Objects;

class Guard {

  private Coordinate coordinate;
  private Direction direction;
  private boolean on;

  Guard(Coordinate coordinate, Direction direction, boolean on) {
    this.coordinate = coordinate;
    this.direction = direction;
    this.on = on;
  }

  Guard(Guard guard) {
    this.coordinate = new Coordinate(guard.coordinate.x(), guard.coordinate.y());
    this.direction = guard.direction;
    this.on = guard.on;
  }

  Character lookAhead(Map<Coordinate, Character> labLayout) {
    return switch (direction) {
      case NORTH -> labLayout.get(new Coordinate(coordinate.x(), coordinate.y() + 1));
      case EAST -> labLayout.get(new Coordinate(coordinate.x() + 1, coordinate.y()));
      case SOUTH -> labLayout.get(new Coordinate(coordinate.x(), coordinate.y() - 1));
      case WEST -> labLayout.get(new Coordinate(coordinate.x() - 1, coordinate.y()));
    };
  }

  void walk() {
    switch (direction) {
      case NORTH -> this.coordinate = new Coordinate(coordinate.x(), coordinate.y() + 1);
      case EAST -> this.coordinate = new Coordinate(coordinate.x() + 1, coordinate.y());
      case SOUTH -> this.coordinate = new Coordinate(coordinate.x(), coordinate.y() - 1);
      case WEST -> this.coordinate = new Coordinate(coordinate.x() - 1, coordinate.y());
    }
  }

  void turnRight() {
    final int value = Math.floorMod(direction.ordinal() + 1, Direction.values().length);
    this.direction = Direction.values()[value];
  }

  Coordinate getCoordinate() {
    return this.coordinate;
  }

  Direction getDirection() {
    return this.direction;
  }

  boolean isOn() {
    return this.on;
  }

  void setOn(boolean on) {
    this.on = on;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Guard guard = (Guard) o;
    return on == guard.on && Objects.equals(coordinate, guard.coordinate) && direction == guard.direction;
  }

  @Override
  public int hashCode() {
    return Objects.hash(coordinate, direction, on);
  }

  @Override
  public String toString() {
    return "Guard{" +
        "coordinate=" + coordinate +
        ", direction=" + direction +
        ", on=" + on +
        '}';
  }

}
