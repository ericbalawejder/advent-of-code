package aoc.year2020.day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonsterMessages {

    private final Map<Integer, List<List<String>>> rulesMap;
    private final List<String> messages;

    public static void main(String[] args) throws IOException {
        final List<String> rulesAndMessages =
                readRulesAndMessages("src/main/java/aoc/year2020/day19/received-messages.txt");
        final MonsterMessages monster = new MonsterMessages(rulesAndMessages);
        System.out.println(monster.countValidMessages(monster.getMessages(), 0));

        final List<String> modifiedRulesAndMessages =
                readRulesAndMessages("src/main/java/aoc/year2020/day19/modified-messages.txt");
        final MonsterMessages monster2 = new MonsterMessages(modifiedRulesAndMessages);
        System.out.println(monster2.countValidMessages(monster2.getMessages(), 0));
    }

    MonsterMessages(List<String> data) {
        this.rulesMap = findRules(data);
        this.messages = findMessages(data);
    }

    long countValidMessages(List<String> messages, int ruleNumber) {
        return messages.stream()
                .map(message -> evaluate(message, ruleNumber))
                .filter(list -> !list.isEmpty())
                .filter(list -> list.stream().anyMatch(String::isEmpty))
                .count();
    }

    Map<Integer, List<List<String>>> getRulesMap() {
        return Map.copyOf(rulesMap);
    }

    List<String> getMessages() {
        return List.copyOf(messages);
    }

    private List<String> evaluate(String message, int ruleNumber) {
        final List<List<String>> rules = rulesMap.get(ruleNumber);
        final List<String> result = new ArrayList<>();

        for (List<String> rule : rules) {
            if (rule.get(0).equals("a") || rule.get(0).equals("b")) {
                return message.startsWith(rule.get(0)) ?
                        List.of(message.substring(1)) :
                        Collections.emptyList();
            }
            List<String> expressions = List.of(message);
            for (String value : rule) {
                expressions = expressions.stream()
                        .map(exp -> evaluate(exp, Integer.parseInt(value)))
                        .flatMap(Collection::stream)
                        .toList();
                if (expressions.isEmpty()) {
                    break;
                }
            }
            if (!expressions.isEmpty()) {
                result.addAll(expressions);
            }
        }
        return List.copyOf(result);
    }

    private List<String> findMessages(List<String> data) {
        return Stream.of(data.get(1).split("\\n"))
                .toList();
    }

    private Map<Integer, List<List<String>>> findRules(List<String> data) {
        return Stream.of(data.get(0).split("\\n"))
                .map(s -> s.split(": "))
                .collect(Collectors.toMap(a -> Integer.parseInt(a[0]), a -> parseRules(a[1])));
    }

    private List<List<String>> parseRules(String rules) {
        return Stream.of(rules.split(" \\| "))
                .map(subRule -> subRule.split(" "))
                .map(Arrays::asList)
                .toList();
    }

    private static List<String> readRulesAndMessages(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replace("\"", ""))
                .toList();
    }

}
