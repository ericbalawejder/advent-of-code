package aoc.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collector;

public class CustomCustomsPart2 {

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/day6/group-answers.txt";
        String[] data = readFromFile(path);
        System.out.println(sumOfCountsEveryone(data));
    }

    static int sumOfCountsEveryone(String[] groupAnswers) {
        return Arrays.stream(groupAnswers)
                .map(CustomCustomsPart2::intersectingAnswers)
                .map(String::length)
                .reduce(0, Integer::sum);
    }

    private static String intersectingAnswers(String group) {
        return Arrays.stream(group.split("-"))
                .reduce(CustomCustomsPart2::intersection)
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

    private static String[] readFromFile(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\n", "-"))
                .toArray(String[]::new);
    }
}
