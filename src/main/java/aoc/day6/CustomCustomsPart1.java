package aoc.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomCustomsPart1 {

    public static void main(String[] args) throws IOException {
        final String[] data = readFile("src/main/java/aoc/day6/group-answers.txt");
        System.out.println(sumOfCounts(data));
    }

    static int sumOfCounts(String[] groupAnswers) {
        return countAnswers(groupAnswers).stream()
                .map(Set::size)
                .reduce(0, Integer::sum);
    }

    private static List<Set<Character>> countAnswers(String[] groupAnswers) {
        return Arrays.stream(groupAnswers)
                .map(s -> s.chars().mapToObj(c -> (char) c)
                    .collect(Collectors.toUnmodifiableSet()))
                .collect(Collectors.toUnmodifiableList());
    }

    private static String[] readFile(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\n", ""))
                .toArray(String[]::new);
    }

}
