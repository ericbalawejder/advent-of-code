package aoc.day9;

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
        final List<Long> xmas = readFromFile("src/main/java/aoc/day9/xmas.txt");
        System.out.println(findInvalidNumber(xmas));
        System.out.println(findEncryptionWeakness(xmas));
    }

    static Optional<Long> findEncryptionWeakness(List<Long> xmas) {
        Optional<Long> invalidNumber = findInvalidNumber(xmas);
        if (!invalidNumber.isPresent()) {
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
            List<Long> subList = xmas.subList(start, i);
            if (!twoSum(subList, xmas.get(i))) {
                return Optional.of(xmas.get(i));
            }
            start++;
        }
        return Optional.empty();
    }

    private static Long sumMinMax(List<Long> subList) {
        return subList.stream()
                .min(Long::compareTo)
                .orElse(0L)
                +
                subList.stream()
                        .max(Long::compareTo)
                        .orElse(0L);
    }

    private static boolean twoSum(List<Long> numbers, long target) {
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < numbers.size(); i++) {
            if (set.contains(numbers.get(i))) {
                return true;
            } else {
                set.add(target - numbers.get(i));
            }
        }
        return false;
    }

    private static List<Long> readFromFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(Long::parseLong)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

