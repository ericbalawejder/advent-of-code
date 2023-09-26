package aoc.year2020.day23;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CrabTest {

  public static void main(String[] args) throws IOException {
    //final String labels = "467528193"; // data
    final List<Integer> cups = labelCups("389125467");

    System.out.println(play(cups, 10));
  }

  static List<Integer> play(List<Integer> gameCups, int moves) {
    List<Integer> cups = List.copyOf(gameCups);
    int currentCup = cups.get(0);

    for (int i = 0; i < moves; i++) {
      List<Integer> pickUp = cups.subList(i + 1, i + 4);
      System.out.println("start---------" + (i + 1));
      System.out.println("cups=" + cups);
      System.out.println("pickUp= " + pickUp);
      List<Integer> remainingCups = difference(cups, pickUp);
      System.out.println("remainingCups= " + remainingCups);

      final Map<String, Integer> minMax = remainingCups.stream()
          .collect(
              Collectors.teeing(
                  Collectors.maxBy(Integer::compareTo),
                  Collectors.minBy(Integer::compareTo),
                  (e1, e2) -> {
                    Map<String, Integer> map = new HashMap<>();
                    map.put("MAX", e1.get());
                    map.put("MIN", e2.get());
                    return map;
                  }
              ));

      final int lowestCup = minMax.get("MIN");

      final int highestCup = remainingCups.stream()
          .max(Integer::compareTo)
          .orElseThrow();

      System.out.println("lowest= " + lowestCup);
      System.out.println("highest= " + highestCup);
      final int cup = currentCup;
      System.out.println("currentCup= " + currentCup);
      final int destinationCup = IntStream.rangeClosed(1, currentCup - lowestCup)
          .filter(n -> remainingCups.contains(cup - n))
          .map(n -> remainingCups.indexOf(cup - n))
          .map(remainingCups::get)
          .findFirst()
          .orElse(highestCup);

      System.out.println("destinationCup= " + destinationCup);
      cups = combine(remainingCups, pickUp, remainingCups.indexOf(destinationCup));
      currentCup = destinationCup;
      System.out.println("finish= " + cups);
    }
    return List.copyOf(cups);
  }

  private static <T> List<T> difference(final List<T> listA, final List<T> listB) {
    return listA.stream()
        .filter(e -> !listB.contains(e))
        .toList();
  }

  private static <T> List<T> combine(final List<T> listA, final List<T> listB, int index) {
    final List<T> a = listA.subList(0, index + 1);
    final List<T> c = listA.subList(index + 1, listA.size());
    return concatenate(a, listB, c);
  }

  @SafeVarargs
  private static <T> List<T> concatenate(List<T>... lists) {
    return Stream.of(lists)
        .flatMap(Collection::stream)
        .toList();
  }

  private static List<Integer> labelCups(String labels) {
    return Arrays.stream(labels.split(""))
        .map(Integer::parseInt)
        .toList();
  }

}
