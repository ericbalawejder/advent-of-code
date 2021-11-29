package aoc.year2020.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolicyValidatorPart1 {

    public static void main(String[] args) {
        final List<String> input = readFromFile("src/main/java/aoc/year2020/day2/passwords.txt");
        System.out.println(passwordValidator(input).size());
    }

    static List<String> passwordValidator(List<String> input) {
        final List<String> validPasswords = new ArrayList<>();
        for (String policy : input) {
            final String[] tuple = policy.split(" ");
            final String[] range = tuple[0].split("-");
            final char character = tuple[1].charAt(0);
            final String password = tuple[2];
            final long min = Integer.parseInt(range[0]);
            final long max = Integer.parseInt(range[1]);
            if (isValid(min, max, character, password)) {
                validPasswords.add(policy);
            }
        }
        return List.copyOf(validPasswords);
    }

    private static boolean isValid(long min, long max, char character, String password) {
        final long count = password.chars()
                .filter(c -> c == character)
                .count();

        return count >= min && count <= max;
    }

    private static List<String> readFromFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
