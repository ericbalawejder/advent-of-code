package aoc.year2021.day5;

public record Point(int x, int y) {

    public Point plus(Point point) {
        return new Point(x + point.x, y + point.y);
    }

}
