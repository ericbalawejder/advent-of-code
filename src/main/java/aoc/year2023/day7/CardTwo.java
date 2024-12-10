package aoc.year2023.day7;

import java.util.Map;
import java.util.Set;

record CardTwo(Character symbol) implements Comparable<CardTwo> {

  private static final Set<Character> SYMBOLS = Set.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
  private static final Map<Character, Integer> VALUE = Map.ofEntries(
      Map.entry('A', 13),
      Map.entry('K', 12),
      Map.entry('Q', 11),
      Map.entry('T', 10),
      Map.entry('9', 9),
      Map.entry('8', 8),
      Map.entry('7', 7),
      Map.entry('6', 6),
      Map.entry('5', 5),
      Map.entry('4', 4),
      Map.entry('3', 3),
      Map.entry('2', 2),
      Map.entry('J', 1)
  );

  CardTwo {
    if (!SYMBOLS.contains(symbol)) {
      throw new IllegalArgumentException("card is not a valid symbol");
    }
  }

  @Override
  public int compareTo(CardTwo cardTwo) {
    return getValue().compareTo(cardTwo.getValue());
  }

  Integer getValue() {
    return VALUE.get(this.symbol);
  }

  boolean isAJoker() {
    return symbol().equals('J');
  }

}
