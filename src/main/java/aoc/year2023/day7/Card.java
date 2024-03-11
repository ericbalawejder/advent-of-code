package aoc.year2023.day7;

import java.util.Map;
import java.util.Set;

record Card(Character symbol) implements Comparable<Card> {

  private static final Set<Character> SYMBOLS = Set.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
  private static final Map<Character, Integer> VALUE = Map.ofEntries(
      Map.entry('A', 14),
      Map.entry('K', 13),
      Map.entry('Q', 12),
      Map.entry('J', 11),
      Map.entry('T', 10),
      Map.entry('9', 9),
      Map.entry('8', 8),
      Map.entry('7', 7),
      Map.entry('6', 6),
      Map.entry('5', 5),
      Map.entry('4', 4),
      Map.entry('3', 3),
      Map.entry('2', 2)
  );

  Card {
    if (!SYMBOLS.contains(symbol)) {
      throw new IllegalArgumentException("card is not a valid symbol");
    }
  }

  @Override
  public int compareTo(Card card) {
    return getValue().compareTo(card.getValue());
  }

  Integer getValue() {
    return VALUE.get(this.symbol);
  }

}
