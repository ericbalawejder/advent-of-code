package aoc.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TwoSum {

    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        List<Integer> values = twoSum.readFromFile("src/main/java/aoc/day1/day1input.txt");
        System.out.println(twoSum.findSum(values, 2020));
        System.out.println(twoSum.listProduct(twoSum.findSum(values, 2020)));
    }

    List<Integer> listProduct(List<List<Integer>> lists) {
        return lists.stream()
                .map(list -> list.stream()
                .reduce(1, (a, b) -> a * b))
                .collect(Collectors.toUnmodifiableList());
    }

    List<List<Integer>> findSum(List<Integer> list, int target) {
        List<List<Integer>> values = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if (map.containsKey(list.get(i))) {
                values.add(Arrays.asList(map.get(list.get(i)), list.get(i)));
            } else {
                map.put(target - list.get(i), list.get(i));
            }
        }
        return Collections.unmodifiableList(values);
    }

    private int max(List<Integer> list) {
        return list.stream()
                .max(Integer::compareTo)
                .orElse(0);
    }

    // https://stackabuse.com/java-read-a-file-into-an-arraylist/
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
