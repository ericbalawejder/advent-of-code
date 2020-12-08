package aoc.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolicyValidatorPart2 {

    public static void main(String[] args) {
        final List<String> passwordRules = readFromFile("src/main/java/aoc/day2/passwords.txt");
        System.out.println(passwordValidator(passwordRules));
    }

    static int passwordValidator(List<String> input) {
        final List<String> validPasswords = new ArrayList<>();
        for (String policy : input) {
            final String[] tuple = policy.split(" ");
            final String[] indexes = tuple[0].split("-");
            final char character = tuple[1].charAt(0);
            final String password = tuple[2];
            final int index1 = Integer.parseInt(indexes[0]);
            final int index2 = Integer.parseInt(indexes[1]);

            if (isValid(index1, index2, character, password)) {
                validPasswords.add(policy);
            }
        }
        return validPasswords.size();
    }

    private static boolean isValid(int index1, int index2, char character, String password) {
        return password.charAt(index1 - 1) == character
                ^ password.charAt(index2 - 1) == character;
    }

    private static List<String> readFromFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toCollection(ArrayList::new));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
