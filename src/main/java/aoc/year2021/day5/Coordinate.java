package aoc.year2021.day5;

public record Coordinate(Point a) {

    public Coordinate plus(Coordinate coordinate) {
        final Point sum = new Point(a().x() + coordinate.a().x(), a().y() + coordinate.a().y());
        return new Coordinate(sum);
    }

}
