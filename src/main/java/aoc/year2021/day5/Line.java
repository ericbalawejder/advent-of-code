package aoc.year2021.day5;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Line {

    private final Point a;
    private final Point b;
    private final Set<Point> points;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
        this.points = pointsInLine(a, b);
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Set<Point> getPoints() {
        return Set.copyOf(points);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return a.equals(line.a) && b.equals(line.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "Line{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    private static Set<Point> pointsInLine(Point a, Point b) {
        final Set<Point> points = new HashSet<>();
        if (a.x() == b.x()) {
            for (int i = 0; i <= Math.abs(a.y() - b.y()); i++) {
                points.add(new Point(a.x(), a.y() + i));
            }
        } else if (a.y() == b.y()) {
            for (int i = 0; i <= Math.abs(a.x() - b.x()); i++) {
                points.add(new Point(a.x() + i, a.y()));
            }
        }
        return Set.copyOf(points);
    }

}
