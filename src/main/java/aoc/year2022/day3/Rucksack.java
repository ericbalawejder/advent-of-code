package aoc.year2022.day3;

import java.util.Set;
import java.util.stream.Collectors;

record Rucksack(Set<Character> compartment1, Set<Character> compartment2) {

  static int getPriority(Character c) {
    return Character.isUpperCase(c) ? (int) c - 38 : (int) c - 96;
  }

  static <T> Set<T> intersection(final Set<T> setA, final Set<T> setB) {
    return setA.stream()
        .filter(setB::contains)
        .collect(Collectors.toUnmodifiableSet());
  }

}
