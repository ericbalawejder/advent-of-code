package aoc.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HandyHaversacks {

    private static Map<String, String> bagMap;

    public static void main(String[] args) throws IOException {
        bagMap = readFromFileToMap("src/main/java/aoc/day7/bag-rules.txt");
        System.out.println(containsShinyGold());
        System.out.println(countChildBags("shiny gold"));
    }

    static long containsShinyGold() {
        return bagMap.keySet()
                .stream()
                .filter(HandyHaversacks::containsShinyGold)
                .count();
    }

    static int countChildBags(String bagColor) {
        final String bagContent = bagMap.get(bagColor);
        final Matcher matcher = Pattern.compile("(\\d)\\s(\\w+\\s\\w+)")
                .matcher(bagContent);

        int count = 0;
        while (matcher.find()) {
            int amount = Integer.parseInt(matcher.group(1));
            String bagName = matcher.group(2);
            count += amount + (amount * countChildBags(bagName));
        }
        return count;
    }

    private static boolean containsShinyGold(String parentBag) {
        final String bagContent = bagMap.get(parentBag);
        final Matcher matcher = Pattern.compile("(\\d)\\s(\\w+\\s\\w+)")
                .matcher(bagContent);

        List<String> childBags = new ArrayList<>();
        while (matcher.find()) {
            if (matcher.group(2).equals("shiny gold")) {
                return true;
            }
            childBags.add(matcher.group(2));
        }
        return childBags.stream()
                .anyMatch(HandyHaversacks::containsShinyGold);
    }

    private static String parentBag(String bagRule) {
        final Matcher matcher = Pattern.compile("^(\\w+\\s\\w+)").matcher(bagRule);
        matcher.find();
        return matcher.group(0);
    }

    private static Map<String, String> readFromFileToMap(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toMap(
                    HandyHaversacks::parentBag, Function.identity())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
