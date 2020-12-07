package aoc.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCustoms {

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/day6/group-answers.txt";
        String[] data1 = readFromFilePart1(path);
        System.out.println(sumOfCounts(data1));

        String[] data2 = readFromFilePart2(path);
        System.out.println(sumOfCountsEveryone(data2));
    }

    // Part 1
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

    // Part 2
    static int sumOfCountsEveryone(String[] groupAnswers) {
        return Arrays.stream(groupAnswers)
                .map(CustomCustoms::intersectingAnswers)
                .map(String::length)
                .reduce(0, Integer::sum);
    }

    private static String intersectingAnswers(String group) {
        return Arrays.stream(group.split("-"))
                .reduce(CustomCustoms::intersection)
                .orElse("");
    }

    private static String intersection(String s1, String s2) {
        return s1.chars()
                .filter(c -> s2.indexOf(c) != -1)
                .mapToObj(c -> (char) c)
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

    private static String[] readFromFilePart1(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\n", ""))
                .toArray(String[]::new);
    }

    private static String[] readFromFilePart2(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\n", "-"))
                .toArray(String[]::new);
    }
}
