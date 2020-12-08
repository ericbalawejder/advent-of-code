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
        List<Integer> values = readFromFile("src/main/java/aoc/day1/day1input.txt");
        System.out.println(findSum(values, 2020));
        System.out.println(listProduct(findSum(values, 2020)));
    }

    static List<Integer> listProduct(List<List<Integer>> lists) {
        return lists.stream()
                .map(list -> list.stream()
                .reduce(1, (a, b) -> a * b))
                .collect(Collectors.toUnmodifiableList());
    }

    static List<List<Integer>> findSum(List<Integer> list, int target) {
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

    // https://stackabuse.com/java-read-a-file-into-an-arraylist/
    private static List<Integer> readFromFile(String path) {
        // Using try-with-resources so the stream closes automatically
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
