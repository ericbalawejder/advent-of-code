package aoc.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TicketTranslation {

    public static void main(String[] args) throws IOException {
        final String filePath = "src/main/java/aoc/day16/tickets.txt";
        final List<String> ticketRulesAndNumbers = readTicketRulesAndNumbers(filePath);
        //System.out.println(tickets);
        //System.out.println(findRuleNumbers(tickets.get(0)));

        final List<Optional<Integer>> tickets = findInvalidTickets(
                ticketRulesAndNumbers.get(2),
                findRuleNumbers(ticketRulesAndNumbers.get(0)));

        System.out.println(tickets);

        final Integer ticketErrorRate = tickets.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Integer::sum)
                .orElse(0);

        System.out.println(ticketErrorRate);
    }

    private static List<Optional<Integer>> findInvalidTickets(
            String input1, Map<String, Predicate<Integer>> rules) {

        return Stream.of(input1.split("\\n"))
                .skip(1)
                .map(s -> Stream.of(s.split(","))
                        .map(Integer::valueOf)
                        .collect(Collectors.toUnmodifiableList()))
                .map(ticket -> hasInvalidFields(ticket, rules))
                .collect(Collectors.toUnmodifiableList());
    }

    private static Optional<Integer> hasInvalidFields(
            List<Integer> ticket, Map<String, Predicate<Integer>> rules) {

        for (Integer field : ticket) {
            if(rules.values().stream().noneMatch(rule -> rule.test(field))) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
    }





    private static List<List<Integer>> findValidTickets(String input1, Map<String, Predicate<Integer>> rules) {
        return Stream.of(input1.split("\\n"))
                .skip(1)
                .map(s -> Stream.of(s.split(","))
                        .map(Integer::valueOf)
                        .collect(Collectors.toUnmodifiableList()))
                .filter(ticket -> hasValidFields(ticket, rules))
                .collect(Collectors.toUnmodifiableList());
    }

    private static boolean hasValidFields(List<Integer> ticket, Map<String, Predicate<Integer>> rules) {
        for (Integer field : ticket) {
            if(rules.values().stream().noneMatch(rule -> rule.test(field))) {
                return false;
            }
        }
        return true;
    }

    private static Map<String, Predicate<Integer>> findRuleNumbers(String rules) {
        return Stream.of(rules.split("\\n"))
                .map(s -> s.split(": "))
                .collect(Collectors.toUnmodifiableMap(
                        a -> a[0], a -> toPredicate(a[1])));
    }

    private static Predicate<Integer> toPredicate(String s) {
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
                //.map(s -> s.replace("\n", ""))
                .collect(Collectors.toUnmodifiableList());
    }

}
