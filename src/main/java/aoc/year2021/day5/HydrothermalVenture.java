package aoc.year2021.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HydrothermalVenture {

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/year2021/day5/test.txt";
        final List<Line> lines = getLines(path);
        //System.out.println(lines);
        //System.out.println(lines.size());
        final CartesianCoordinate plane = new CartesianCoordinate(10, 10);
        final Line p = new Line(new Point(3, 2), new Point(7, 2));
        //System.out.println(p.getPoints());
        System.out.println(drawLines(plane, List.of(p)));
    }

    static int countOverlap(List<Line> lines) {

        return 0;
    }

    private static CartesianCoordinate drawLines(CartesianCoordinate plane, List<Line> lines) {
        for (Line line : lines) {
            final int x1 = line.getA().x();
            final int y1 = line.getA().y();
            final int x2 = line.getB().x();
            final int y2 = line.getB().y();
            plane.getGrid()[y1][x1]++;
            plane.getGrid()[y2][x2]++;
        }
        return new CartesianCoordinate(plane);
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

    private static List<Line> getLines2(String path) {
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

}
