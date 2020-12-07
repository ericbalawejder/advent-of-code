package aoc.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomCustoms {

    public static void main(String[] args) throws IOException {
        String[][] data = readFromFile("src/main/java/aoc/day6/test.txt");
        System.out.println(Arrays.deepToString(data));
        System.out.println(countAnswers2(data));

        System.out.println(sumOfCounts(countAnswers2(data)));
    }

    static int sumOfCounts(List<Set<Character>> counts) {
        return counts.stream()
                .map(Set::size)
                .reduce(0, Integer::sum);
    }

    static List<Set<Character>> countAnswers(String[][] groupAnswers) {
        final List<Set<Character>> uniqueAnswers = new ArrayList<>();
        for (String[] group : groupAnswers) {
            Set<Character> yes = Arrays.stream(group)
                    .flatMap(s -> s.chars().mapToObj(c -> (char) c))
                    .collect(Collectors.toUnmodifiableSet());

            uniqueAnswers.add(yes);
        }
        return List.copyOf(uniqueAnswers);
    }

    static List<Set<Character>> countAnswers2(String[][] groupAnswers) {
        return Arrays.stream(groupAnswers)
                .map(array -> Arrays.stream(array)
                    .flatMap(s -> s.chars().mapToObj(c -> (char) c))
                    .collect(Collectors.toUnmodifiableSet()))
                .collect(Collectors.toUnmodifiableList());
    }

    private static String[][] readFromFile(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\n", ""))
                .map(s -> s.split(" "))
                .toArray(String[][]::new);
    }
}
