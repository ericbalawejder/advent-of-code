package aoc.year2020.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PolicyValidatorPart1 {

    public static void main(String[] args) {
        final List<Password> passwordRules = readFile("src/main/java/aoc/year2020/day2/passwords.txt");
        System.out.println(passwordValidator(passwordRules).size());
        System.out.println(passwordValidatorPart2(passwordRules));
    }

    static int passwordValidatorPart2(List<Password> passwords) {
        return passwords.stream()
                .filter(PolicyValidatorPart1::isValidPart2)
                .toList()
                .size();
    }

    static List<Password> passwordValidator(List<Password> passwords) {
        return passwords.stream()
                .filter(PolicyValidatorPart1::isValid)
                .toList();
    }

    private static boolean isValidPart2(Password password) {
        return password.getText().charAt(password.getMin() - 1) == password.getCharacter()
                ^ password.getText().charAt(password.getMax() - 1) == password.getCharacter();
    }

    private static boolean isValid(Password password) {
        final long count = password.getText()
                .chars()
                .filter(c -> c == password.getCharacter())
                .count();

        return count >= password.getMin() && count <= password.getMax();
    }

    private static List<Password> readFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(Password::new).toList();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
