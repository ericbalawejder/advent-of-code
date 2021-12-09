package aoc.year2021.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HydrothermalVenture {

    public static void main(String[] args) {
        final String path = "src/main/java/aoc/year2021/day5/lines-of-vents.txt";
        final List<Line> lines = getLines(path);
        System.out.println(countOverlap(lines));
        System.out.println(countOverlapWithDiagonals(lines));
    }

    static int countOverlapWithDiagonals(List<Line> lines) {
        return generateAllPointsFromLines(lines.stream()
                .filter(Line::isVerticalOrHorizontalOrDiagonal));
    }

    static int countOverlap(List<Line> lines) {
        return generateAllPointsFromLines(lines.stream()
                .filter(Line::isVerticalOrHorizontal));
    }

    private static int generateAllPointsFromLines(Stream<Line> lineStream) {
        return (int) lineStream
                .map(HydrothermalVenture::generateAllPointsOnLine)
                .toList()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.reducing(0, e -> 1, Integer::sum)))
                .values()
                .stream()
                .filter(v -> v > 1)
                .count();
    }

    private static List<Point> generateAllPointsOnLine(Line line) {
        final Point step = new Point((int) Math.signum(line.b().x() - line.a().x()),
                (int) Math.signum(line.b().y() - line.a().y()));

        final Point startPoint = new Point(line.a().x(), line.a().y());

        return Stream.iterate(startPoint, point -> point.add(step))
                .limit(line.length())
                .toList();
    }

    private static List<Line> getLines(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(s -> s.replace(" -> ", " "))
                    .map(s -> s.replace(",", " "))
                    .map(List::of)
                    .map(HydrothermalVenture::getPoints)
                    .flatMap(List::stream)
                    .map(list -> new Line(list.get(0), list.get(1)))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static List<List<Point>> getPoints(List<String> points) {
        return points.stream()
                .map(s -> s.split(" "))
                .map(a -> List.of(
                        new Point(Integer.parseInt(a[0]), Integer.parseInt(a[1])),
                        new Point(Integer.parseInt(a[2]), Integer.parseInt(a[3]))))
                .toList();
    }

    /*
    // Get data using regex
    private static List<Line> getLines(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(HydrothermalVenture::getLine)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static Line getLine(String line) {
        final Pattern linePattern = Pattern.compile("^(\\d+),(\\d+) -> (\\d+),(\\d+)$");
        final Matcher matcher = linePattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("bad line input: " + line);
        }
        final int x1 = Integer.parseInt(matcher.group(1));
        final int y1 = Integer.parseInt(matcher.group(2));
        final int x2 = Integer.parseInt(matcher.group(3));
        final int y2 = Integer.parseInt(matcher.group(4));

        return new Line(new Point(x1, y1), new Point(x2, y2));
    }
     */

}
