package aoc.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreeSum {

    public static void main(String[] args) {
        ThreeSum sum = new ThreeSum();
        List<Integer> values = sum.readFromFile("src/main/java/aoc/day1/day1input.txt");
        System.out.println(sum.threeSum(values, 2020));
        System.out.println(sum.calculateProduct(sum.threeSum(values, 2020)));
    }

    List<List<Integer>> threeSum(List<Integer> values, int target) {
        if (values.size() < 3) return new ArrayList<>();

        final Set<List<Integer>> set = new HashSet<>();

        final List<Integer> sorted = values.stream()
                .sorted()
                .collect(Collectors.toUnmodifiableList());

        final int n = sorted.size();
        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int sum = sorted.get(i) + sorted.get(left) + sorted.get(right);
                if (sum == target) {
                    set.add(Arrays.asList(sorted.get(i), sorted.get(left++), sorted.get(right--)));
                }
                else if (sum > target) {
                    right--;
                }
                else {
                    left++;
                }
            }
        }
        return List.copyOf(set);
    }

    List<Integer> calculateProduct(List<List<Integer>> lists) {
        return lists.stream()
                .map(list -> list.stream()
                        .reduce(1, (a, b) -> a * b))
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Integer> readFromFile(String path) {
        List<Integer> list = new ArrayList<>();

        // Using try-with-resources so the stream closes automatically
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            list = stream.map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableList(list);
    }
}
