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

    private final Map<String, String> bagMap;

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/day7/bag-rules.txt";
        final HandyHaversacks haversacks = new HandyHaversacks(readFile(path));
        System.out.println(haversacks.containsShinyGold());
        System.out.println(haversacks.countChildBags("shiny gold"));
    }

    HandyHaversacks(Map<String, String> bagMap) {
        this.bagMap = bagMap;
    }

    long containsShinyGold() {
        return bagMap.keySet()
                .stream()
                .filter(this::containsShinyGold)
                .count();
    }

    int countChildBags(String bagColor) {
        final String bagContent = bagMap.get(bagColor);
        final Matcher matcher = Pattern.compile("(\\d)\\s(\\w+\\s\\w+)")
                .matcher(bagContent);

        int count = 0;
        while (matcher.find()) {
            int amount = Integer.parseInt(matcher.group(1));
            final String bagName = matcher.group(2);
            count += amount + (amount * countChildBags(bagName));
        }
        return count;
    }

    private boolean containsShinyGold(String parentBag) {
        final String bagContent = bagMap.get(parentBag);
        final Matcher matcher = Pattern.compile("(\\d)\\s(\\w+\\s\\w+)")
                .matcher(bagContent);

        final List<String> childBags = new ArrayList<>();
        while (matcher.find()) {
            if (matcher.group(2).equals("shiny gold")) {
                return true;
            }
            childBags.add(matcher.group(2));
        }
        return childBags.stream()
                .anyMatch(this::containsShinyGold);
    }

    private static Map<String, String> readFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toMap(
                    HandyHaversacks::parentBag, Function.identity())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private static String parentBag(String bagRule) {
        final Matcher matcher = Pattern.compile("^(\\w+\\s\\w+)").matcher(bagRule);
        matcher.find();
        return matcher.group(0);
    }

}
