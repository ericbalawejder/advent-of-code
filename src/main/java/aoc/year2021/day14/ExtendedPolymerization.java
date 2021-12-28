package aoc.year2021.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtendedPolymerization {

    /**
     * Use TreeMap to sort by value -> occurrences of letters
     * use hashmap, sort values, get min and max key
     */
    public static void main(String[] args) throws IOException {
        //final String path = "src/main/java/aoc/year2021/day14/pair-insertion-rules.txt";
        final String path = "src/main/java/aoc/year2021/day14/test.txt";
        final Optional<String> polymerTemplate = getPolymerTemplate(path);
        final Map<String, String> pairInsertionRules = getPairInsertionRules(path);

        final String polymer = generatePolymer(pairInsertionRules, polymerTemplate.orElseThrow(), 40);
        System.out.println(polymer.length());
        System.out.println(frequency(polymer));
        System.out.println(polymerMath(polymer));
    }

    static long polymerMath(String polymer) {
        final List<Map.Entry<Character, Long>> characterCount = frequency(polymer);
        final long max = characterCount.get(characterCount.size() - 1).getValue();
        final long min = characterCount.get(0).getValue();
        return max - min;
    }

    static List<Map.Entry<Character, Long>> frequency(String polymer) {
        final Map<Character, Long> characterCount = polymer.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));

        final Comparator<Map.Entry<Character, Long>> byValue =
                Map.Entry.comparingByValue();

        // use TreeSet
        return characterCount.entrySet()
                .stream()
                .sorted(byValue)
                .toList();
    }

    private static String generatePolymer(Map<String, String> pairInsertionRules,
                                  String polymerTemplate,
                                  int steps) {
        String polymer = polymerTemplate;
        for (int i = 0; i < steps; i++) {
            polymer = polymerStep(pairInsertionRules, polymer);
        }
        return polymer;
    }

    private static String polymerStep(Map<String, String> pairInsertionRules, String polymerTemplate) {
        final StringBuilder polymer = new StringBuilder(polymerTemplate.substring(0, 1));
        for (int i = 0; i <= polymerTemplate.length() - 2; i++) {
            final String pair = polymerTemplate.substring(i, i + 2);
            final String[] s = pair.split("");
            if (pairInsertionRules.containsKey(pair)) {
                final String value = pairInsertionRules.get(pair);
                polymer.append(value);
                polymer.append(s[1]);
            } else {
                polymer.append(s[1]);
            }
        }
        return polymer.toString();
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
