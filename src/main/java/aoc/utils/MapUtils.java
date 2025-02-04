package aoc.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MapUtils {

  // In case of one-to-one relationship, we can return the first matched key.
  public static <T, E> T getKeysByValueT(Map<T, E> map, E value) {
    return map.entrySet()
        .stream()
        .filter(entry -> Objects.equals(entry.getValue(), value))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElseThrow();
  }

  /**
   * Possible one-to-many K -|--<- V
   * Returning empty collection > Optional because if the value is not found, we
   * don't want to throw an exception. We can't use .orElse() to create an instance
   * of a generic class because of type erasure.
   */
  public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
    return map.entrySet()
        .stream()
        .filter(entry -> Objects.equals(entry.getValue(), value))
        .map(Map.Entry::getKey)
        .collect(Collectors.toUnmodifiableSet());
  }

  public static Map<Character, Long> sortMapByValues(Map<Character, Long> map) {
    return map.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .map(e -> Map.entry(e.getKey(), e.getValue()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
  }

}
