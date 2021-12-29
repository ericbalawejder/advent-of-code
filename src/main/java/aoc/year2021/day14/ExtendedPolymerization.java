package aoc.year2021.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ExtendedPolymerization {

    public static void main(String[] args) throws IOException {
        final String path = "src/main/java/aoc/year2021/day14/pair-insertion-rules.txt";
        final Optional<String> polymerTemplate = getPolymerTemplate(path);
        final Map<String, String> pairInsertionRules = getPairInsertionRules(path);
        final long maxMinusMin1 = mostMinusLeast(pairInsertionRules, polymerTemplate.orElseThrow(), 10);
        final long maxMinusMin2 = mostMinusLeast(pairInsertionRules, polymerTemplate.orElseThrow(), 40);
        System.out.println(maxMinusMin1);
        System.out.println(maxMinusMin2);
    }

    static long mostMinusLeast(Map<String, String> pairInsertionRules,
                               String polymerTemplate,
                               int steps) {
        final Map<Character, Long> characterCount =
                countPolymerCharacters(pairInsertionRules, polymerTemplate, steps);

        final List<Map.Entry<Character, Long>> sorted = characterCount.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        return sorted.get(sorted.size() - 1).getValue() - sorted.get(0).getValue();
    }

    private static Map<Character, Long> countPolymerCharacters(Map<String, String> pairInsertionRules,
                                                               String template,
                                                               int steps) {
        final Map<String, Long> pairCount = generatePolymer(pairInsertionRules, template, steps);
        final Map<Character, Long> characterCount = new HashMap<>();
        characterCount.compute(template.charAt(0), (k, v) -> v == null ? 1L : v + 1L);
        characterCount.compute(template.charAt(template.length() - 1), (k, v) -> v == null ? 1L : v + 1L);
        pairCount.forEach((key, value) -> {
            characterCount.compute(key.charAt(0), (k, v) -> v == null ? value : v + value);
            characterCount.compute(key.charAt(1), (k, v) -> v == null ? value : v + value);
        });
        return characterCount.entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey(), e.getValue() / 2))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<String, Long> generatePolymer(Map<String, String> pairInsertionRules,
                                                     String polymerTemplate,
                                                     int steps) {
        final Map<String, Long> polymerTemplatePairCount = polymerTemplatePairCount(polymerTemplate);
        Map<String, Long> pairCount = new HashMap<>(polymerTemplatePairCount);
        for (int i = 0; i < steps; i++) {
            pairCount = step(pairCount, pairInsertionRules);
        }
        return Map.copyOf(pairCount);
    }

    private static Map<String, Long> step(Map<String, Long> pairCount, Map<String, String> rules) {
        final Map<String, Long> polymerPairCount = new HashMap<>();
        for (String key : pairCount.keySet()) {
            final String value = rules.get(key);
            final String p1 = key.charAt(0) + value;
            final String p2 = value + key.charAt(1);
            final long count = pairCount.getOrDefault(key, 0L);
            polymerPairCount.compute(p1, (k, v) -> v == null ? count : v + count);
            polymerPairCount.compute(p2, (k, v) -> v == null ? count : v + count);
        }
        return Map.copyOf(polymerPairCount);
    }

    private static Map<String, Long> polymerTemplatePairCount(String polymerTemplate) {
        return IntStream.range(0, polymerTemplate.length() - 1)
                .mapToObj(i -> polymerTemplate.substring(i, i + 2))
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));
    }

    private static Map<String, String> getPairInsertionRules(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.skip(2)
                    .map(s -> s.split(" -> "))
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(a -> a[0], a -> a[1]),
                            Collections::unmodifiableMap));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    private static Optional<String> getPolymerTemplate(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.findFirst();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
