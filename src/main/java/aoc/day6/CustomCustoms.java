package aoc.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomCustoms {

    public static void main(String[] args) throws IOException {
        String[][] data = readFromFilePart1("src/main/java/aoc/day6/test.txt");
        //System.out.println(Arrays.deepToString(data));
        //System.out.println(countAnswers(data));
        System.out.println(sumOfCounts(countAnswers(data)));
    }

    static int sumOfCounts(List<Set<Character>> counts) {
        return counts.stream()
                .map(Set::size)
                .reduce(0, Integer::sum);
    }

    static List<Set<Character>> countAnswers(String[][] groupAnswers) {
        return Arrays.stream(groupAnswers)
                .map(array -> Arrays.stream(array)
                    .flatMap(s -> s.chars().mapToObj(c -> (char) c))
                    .collect(Collectors.toUnmodifiableSet()))
                .collect(Collectors.toUnmodifiableList());
    }

    private static String[][] readFromFilePart1(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\n", ""))
                .map(s -> s.split(" "))
                .toArray(String[][]::new);
    }
}
