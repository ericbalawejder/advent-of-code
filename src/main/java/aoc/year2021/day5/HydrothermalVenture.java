package aoc.year2021.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class HydrothermalVenture {

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/year2021/day5/test.txt";
        final List<Line> lines = getLines(path);
        System.out.println(lines);
        System.out.println(lines.size());
        final CartesianCoordinate plane = new CartesianCoordinate(10, 10);
        System.out.println(plane);
    }

    static int countOverlap(List<Line> lines) {

        return 0;
    }

    private static CartesianCoordinate drawLines(CartesianCoordinate plane, List<Line> lines) {

        return null;
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

}
