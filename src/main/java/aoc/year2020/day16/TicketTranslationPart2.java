package aoc.year2020.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TicketTranslationPart2 {

    public static void main(String[] args) throws IOException {
        final String filePath = "src/main/java/aoc/year2020/day16/tickets.txt";
        final List<String> ticketRulesAndNumbers = readTicketRulesAndNumbers(filePath);
        System.out.println(productOfDepartureFields(ticketRulesAndNumbers));
    }

    static long productOfDepartureFields(List<String> ticketRulesAndNumbers) {
        final Map<String, Predicate<Integer>> rules =
                findRuleNumbers(ticketRulesAndNumbers.get(0));

        final List<List<Integer>> tickets =
                findValidTickets(ticketRulesAndNumbers.get(2), rules);

        final Map<String, List<Integer>> possibleFieldPositions =
                findPossibleFieldPositions(rules, tickets);

        final List<String> sortedFields = possibleFieldPositions
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().size()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toUnmodifiableList());

        for (int i = sortedFields.size() - 2; i >= 0; i--) {
            final List<Integer> smallerList = possibleFieldPositions.get(sortedFields.get(i));
            final List<Integer> largerList = possibleFieldPositions.get(sortedFields.get(i + 1));
            largerList.removeAll(smallerList);
        }

        final List<Integer> myTicket = findValidTickets(ticketRulesAndNumbers.get(1), rules).get(0);

        return possibleFieldPositions.keySet()
                .stream()
                .filter(s -> s.startsWith("departure"))
                .map(possibleFieldPositions::get)
                .map(list -> list.get(0))
                .mapToLong(myTicket::get)
                .reduce(Math::multiplyExact)
                .orElseThrow(() -> new RuntimeException("departure field not found"));
    }

    private static Map<String, List<Integer>> findPossibleFieldPositions(
            Map<String, Predicate<Integer>> rules, List<List<Integer>> tickets) {

        final Map<String, List<Integer>> positionsMap = new HashMap<>();
        for (int i = 0; i < tickets.get(0).size(); i++) {
            for (Map.Entry<String, Predicate<Integer>> rule : rules.entrySet()) {
                boolean matches = true;
                for (List<Integer> ticket : tickets) {
                    matches &= rule.getValue().test(ticket.get(i));
                }
                if (matches) {
                    List<Integer> positions =
                            positionsMap.getOrDefault(rule.getKey(), new ArrayList<>());
                    positions.add(i);
                    positionsMap.put(rule.getKey(), positions);
                }
            }
        }
        return Collections.unmodifiableMap(positionsMap);
    }

    private static List<List<Integer>> findValidTickets(
            String nearbyTickets, Map<String, Predicate<Integer>> rules) {

        return Stream.of(nearbyTickets.split("\\n"))
                .skip(1)
                .map(s -> Stream.of(s.split(","))
                        .map(Integer::valueOf)
                        .collect(Collectors.toUnmodifiableList()))
                .filter(tickets -> hasValidFields(tickets, rules))
                .collect(Collectors.toUnmodifiableList());
    }

    private static boolean hasValidFields(
            List<Integer> tickets, Map<String, Predicate<Integer>> rules) {

        for (Integer field : tickets) {
            if (rules.values().stream().noneMatch(rule -> rule.test(field))) {
                return false;
            }
        }
        return true;
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
