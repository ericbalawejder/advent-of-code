package aoc.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolicyValidator {

    public static void main(String[] args) {
        PolicyValidator policyValidator = new PolicyValidator();
        final List<String> input = policyValidator.readFromFile("src/main/java/aoc/day2/passwords.txt");
        System.out.println(policyValidator.passwordValidator(input).size());
    }

    List<String> passwordValidator(List<String> input) {
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
        return Collections.unmodifiableList(validPasswords);
    }

    private boolean isValid(long min, long max, char character, String password) {
        final long count = password.chars()
                .filter(c -> c == character)
                .count();

        return count >= min && count <= max;
    }

    // https://stackabuse.com/java-read-a-file-into-an-arraylist/
    private List<String> readFromFile(String path) {
        List<String> list = new ArrayList<>();

        // Using try-with-resources so the stream closes automatically
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            list = stream.collect(Collectors.toCollection(ArrayList::new));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableList(list);
    }
}
