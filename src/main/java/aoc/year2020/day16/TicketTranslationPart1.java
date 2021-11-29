package aoc.year2020.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TicketTranslationPart1 {

    public static void main(String[] args) throws IOException {
        final String filePath = "src/main/java/aoc/year2020/day16/test.txt";
        final List<String> ticketRulesAndNumbers = readTicketRulesAndNumbers(filePath);
        System.out.println(ticketScanningErrorRate(ticketRulesAndNumbers));
    }

    static Integer ticketScanningErrorRate(List<String> ticketRulesAndNumbers) {
        final List<List<Integer>> tickets = findInvalidTickets(
                ticketRulesAndNumbers.get(2),
                findRuleNumbers(ticketRulesAndNumbers.get(0)));

        return tickets.stream()
                .flatMap(List::stream)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private static List<List<Integer>> findInvalidTickets(
            String nearbyTickets, Map<String, Predicate<Integer>> rules) {

        return Stream.of(nearbyTickets.split("\\n"))
                .skip(1)
                .map(s -> Stream.of(s.split(","))
                        .map(Integer::valueOf)
                        .collect(Collectors.toUnmodifiableList()))
                .map(tickets -> findInvalidFields(tickets, rules))
                .collect(Collectors.toUnmodifiableList());
    }

    private static List<Integer> findInvalidFields(
            List<Integer> tickets, Map<String, Predicate<Integer>> rules) {

        return tickets.stream()
                .filter(field -> rules.values()
                        .stream()
                        .noneMatch(rule -> rule.test(field)))
                .collect(Collectors.toUnmodifiableList());
    }

    private static Map<String, Predicate<Integer>> findRuleNumbers(String rules) {
        return Stream.of(rules.split("\\n"))
                .map(s -> s.split(": "))
                .collect(Collectors.toUnmodifiableMap(
                        a -> a[0], a -> predicateStatement(a[1])));
    }

    private static Predicate<Integer> predicateStatement(String s) {
        final Pattern pattern = Pattern.compile("(\\d+)-(\\d+) or (\\d+)-(\\d+)");
        final Matcher matcher = pattern.matcher(s);
        matcher.find();
        final int min1 = Integer.parseInt(matcher.group(1));
        final int max1 = Integer.parseInt(matcher.group(2));
        final int min2 = Integer.parseInt(matcher.group(3));
        final int max2 = Integer.parseInt(matcher.group(4));
        return i -> (i >= min1 && i <= max1) || (i >= min2 && i <= max2);
    }

    private static List<String> readTicketRulesAndNumbers(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .collect(Collectors.toUnmodifiableList());
    }

}
