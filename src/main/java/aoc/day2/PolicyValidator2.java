package aoc.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolicyValidator2 {

    public static void main(String[] args) {
        PolicyValidator2 policyValidator = new PolicyValidator2();
        final List<String> input = policyValidator.readFromFile("src/main/java/aoc/day2/passwords.txt");
        //final List<String> input = Arrays.asList("1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc");
        System.out.println(policyValidator.passwordValidator(input));
    }

    int passwordValidator(List<String> input) {
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

    private boolean isValid(int index1, int index2, char character, String password) {
        return password.charAt(index1 - 1) == character
                ^ password.charAt(index2 - 1) == character;
    }


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
