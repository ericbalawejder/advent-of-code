package aoc.year2020.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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

        groupAnswers.stream()
                .map(individualAnswer -> individualAnswer.chars()
                        .boxed()
                        .collect(Collectors.toSet()))
                .forEach(answeredByEveryone::retainAll);

        return answeredByEveryone.size();
    }

    private static String[] readFile(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\n", "-"))
                .toArray(String[]::new);
    }

}
