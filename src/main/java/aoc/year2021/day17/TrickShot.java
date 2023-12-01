package aoc.year2021.day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TrickShot {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/Day17/target-area.txt";
    final Area target = getTargetArea(path);

    final int highestPoint = findHighestPoint(target);
    System.out.println(highestPoint);

    final long numberOfDistinctInitialVelocities = countAllDistinctInitialVelocities(target);
    System.out.println(numberOfDistinctInitialVelocities);
  }

  private static int findHighestPoint(Area target) {
    return target.velocityCandidates()
        .stream()
        .map(c -> testTrajectory(c.x(), c.y(), target))
        .filter(OptionalInt::isPresent)
        .mapToInt(OptionalInt::getAsInt)
        .max()
        .orElseThrow();
  }

  private static long countAllDistinctInitialVelocities(Area target) {
    return target.velocityCandidates()
        .stream()
        .map(c -> testTrajectory(c.x(), c.y(), target))
        .filter(OptionalInt::isPresent)
        .mapToInt(OptionalInt::getAsInt)
        .count();
  }

  private static OptionalInt testTrajectory(int x, int y, Area target) {
    Coordinate velocity = new Coordinate(x, y);
    Coordinate position = new Coordinate(0, 0);
    OptionalInt maxY = OptionalInt.empty();
    while (target.before(position)) {
      if (maxY.isEmpty() || maxY.getAsInt() < position.y()) {
        maxY = OptionalInt.of(position.y());
      }
      position = position.plus(velocity);
      velocity = velocity.slowDown();
    }
    return target.contains(position) ? maxY : OptionalInt.empty();
  }

  private static Area getTargetArea(String path) {
    try {
      final String target = Files.readString(Paths.get(path));
      final Pattern areaPattern = Pattern.compile("^target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)$");
      final Matcher matcher = areaPattern.matcher(target);
      if (!matcher.matches()) {
        throw new IllegalArgumentException("does not match: " + target);
      }
      int minX = Integer.parseInt(matcher.group(1));
      int maxX = Integer.parseInt(matcher.group(2));
      int minY = Integer.parseInt(matcher.group(3));
      int maxY = Integer.parseInt(matcher.group(4));

      return new Area(new Coordinate(minX, maxY), new Coordinate(maxX, minY));
    } catch (IOException | PatternSyntaxException | OutOfMemoryError | NumberFormatException e) {
      e.printStackTrace();
      return new Area(new Coordinate(0, 0), new Coordinate(0, 0));
    }
  }

}
