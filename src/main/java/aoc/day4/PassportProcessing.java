package aoc.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class PassportProcessing {

    private static final Map<String, String> PASSPORT_POLICY = Map.of(
            "byr", "^(19[2-9][0-9]|200[0-2])$",
            "iyr", "^(201[0-9]|2020)$",
            "eyr", "^(202[0-9]|2030)$",
            "hgt", "^((1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-6])in))$",
            "hcl", "^(#[0-9a-f]{6})$",
            "ecl", "^(amb|blu|brn|gry|grn|hzl|oth)$",
            "pid", "^[0-9]{9}$"
    );

    public static void main(String[] args) throws IOException {
        PassportProcessing passportProcessing = new PassportProcessing();
        String[][] passports = passportProcessing.readFromFile(
                "src/main/java/aoc/day4/passport-data.txt");
        System.out.println(passportProcessing.verifyPassportPart1(passports));
        System.out.println(passportProcessing.verifyPassportPart2(passports));
    }

    long verifyPassportPart1(String[][] passports) {
        return Arrays.stream(passports)
                .filter(this::isPresent)
                .count();
    }

    long verifyPassportPart2(String[][] passports) {
        return Arrays.stream(passports)
                .filter(this::isPresentAndValid)
                .count();
    }

    private boolean isPresent(String[] passport){
        return Arrays.stream(passport)
                .map(s -> s.substring(0, 3))
                .collect(Collectors.toUnmodifiableSet())
                .containsAll(PASSPORT_POLICY.keySet());
    }

    private boolean isPresentAndValid(String[] passport){
        return isPresent(passport) &&
                Arrays.stream(passport)
                .map(s -> s.split(":"))
                .allMatch(s -> matchesRegex(s[0], s[1]));
    }

    private boolean matchesRegex(String key, String value){
        if(!PASSPORT_POLICY.containsKey(key)) { // ¯\_(ツ)_/¯ handles cid
            return true;
        }
        final Pattern pattern = Pattern.compile(PASSPORT_POLICY.get(key));
        return pattern.matcher(value).matches();
    }

    private String[][] readFromFile(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\n", " "))
                .map(s -> s.split(" "))
                .toArray(String[][]::new);
    }
}
