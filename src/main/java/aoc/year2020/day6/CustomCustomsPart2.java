package aoc.year2020.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomCustomsPart2 {

    public static void main(String[] args) throws IOException {
        final String[] answers = readFile("src/main/java/aoc/year2020/day6/group-answers.txt");
        System.out.println(calculateAnyone(answers));
        System.out.println(calculateEveryone(answers));
    }

    private static long calculateAnyone(String[] groupAnswers) {
        return Stream.of(groupAnswers)
                .map(s -> s.replaceAll("-", ""))
                .mapToLong(s -> s.chars().distinct().count())
                .sum();
    }

    private static long calculateEveryone(String[] groupAnswers) {
        return Stream.of(groupAnswers)
                .mapToLong(CustomCustomsPart2::countAnswerByGroup)
                .sum();
    }

    private static long countAnswerByGroup(String answers) {
        final List<String> groupAnswers = List.of(answers.split("-"));

        final Set<Integer> answeredByEveryone = groupAnswers.get(0)
                .chars()
                .boxed()
                .collect(Collectors.toSet());

        final List<Set<Integer>> individualAnswers = groupAnswers.stream()
                .map(individualAnswer -> individualAnswer.chars()
                        .boxed()
                        .collect(Collectors.toSet()))
                .toList();

        final List<Set<Integer>> intersectionOfAnswers = individualAnswers.stream()
                .map(set -> intersection(answeredByEveryone, set))
                .toList();

        final Set<Integer> intersection = intersectionOfAnswers.stream()
                .skip(1)
                .collect(() -> new HashSet<>(intersectionOfAnswers.get(0)), Set::retainAll, Set::retainAll);

        return intersection.size();
    }

    private static <T> Set<T> intersection(final Set<T> setA, final Set<T> setB) {
        return setA.stream()
                .filter(setB::contains)
                .collect(Collectors.toUnmodifiableSet());
    }

    private static String[] readFile(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\n", "-"))
                .toArray(String[]::new);
    }

}
