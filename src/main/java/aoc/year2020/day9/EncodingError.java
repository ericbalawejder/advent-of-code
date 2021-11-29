package aoc.year2020.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EncodingError {

    public static void main(String[] args) {
        final List<Long> xmas = readFromFile("src/main/java/aoc/year2020/day9/xmas.txt");
        System.out.println(findInvalidNumber(xmas));
        System.out.println(findEncryptionWeakness(xmas));
    }

    static Optional<Long> findEncryptionWeakness(List<Long> xmas) {
        final Optional<Long> invalidNumber = findInvalidNumber(xmas);
        if (invalidNumber.isEmpty()) {
            return invalidNumber;
        }
        final Long notValid = invalidNumber.get();
        int start = 0;
        int end = 0;
        Long sum = 0L;
        for (Long number : xmas) {
            sum += number;
            end++;

            while (sum > notValid) {
                sum -= xmas.get(start);
                start++;
            }
            if (sum.equals(notValid)) {
                return Optional.of(sumMinMax(xmas.subList(start, end)));
            }
        }
        return Optional.empty();
    }

    static Optional<Long> findInvalidNumber(List<Long> xmas) {
        final int preamble = 25;
        int start = 0;
        for (int i = preamble; i < xmas.size(); i++) {
            final List<Long> subList = xmas.subList(start, i);
            if (!twoSum(subList, xmas.get(i))) {
                return Optional.of(xmas.get(i));
            }
            start++;
        }
        return Optional.empty();
    }

    private static Long sumMinMax(List<Long> subList) {
        return subList.stream()
                .collect(Collectors.teeing(
                        Collectors.maxBy(Long::compareTo),
                        Collectors.minBy(Long::compareTo),
                        (e1, e2) -> e1.orElseThrow(() -> new IllegalArgumentException("list is empty"))
                                + e2.orElseThrow(() -> new IllegalArgumentException("list is empty"))
                ));
    }

    private static boolean twoSum(List<Long> numbers, long target) {
        final Set<Long> set = new HashSet<>();
        for (Long number : numbers) {
            if (set.contains(number)) {
                return true;
            } else {
                set.add(target - number);
            }
        }
        return false;
    }

    private static List<Long> readFromFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(Long::parseLong).toList();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}

