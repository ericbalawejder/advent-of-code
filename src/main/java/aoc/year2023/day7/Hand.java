package aoc.year2023.day7;

import static aoc.year2023.day7.Type.FIVE_OF_A_KIND;
import static aoc.year2023.day7.Type.FOUR_OF_A_KIND;
import static aoc.year2023.day7.Type.FULL_HOUSE;
import static aoc.year2023.day7.Type.HIGH_CARD;
import static aoc.year2023.day7.Type.ONE_PAIR;
import static aoc.year2023.day7.Type.THREE_OF_A_KIND;
import static aoc.year2023.day7.Type.TWO_PAIR;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

record Hand(List<Card> cards) implements Comparable<Hand> {

  private static final Map<Type, List<Integer>> TYPES = Map.of(
      FIVE_OF_A_KIND, List.of(5),
      FOUR_OF_A_KIND, List.of(1, 4),
      FULL_HOUSE, List.of(2, 3),
      THREE_OF_A_KIND, List.of(1, 1, 3),
      TWO_PAIR, List.of(1, 2, 2),
      ONE_PAIR, List.of(1, 1, 1, 2),
      HIGH_CARD, List.of(1, 1, 1, 1, 1)
  );

  private static final Map<Type, Integer> RANK = Map.of(
      FIVE_OF_A_KIND, 7,
      FOUR_OF_A_KIND, 6,
      FULL_HOUSE, 5,
      THREE_OF_A_KIND, 4,
      TWO_PAIR, 3,
      ONE_PAIR, 2,
      HIGH_CARD, 1
  );

  Type getType() {
    final List<Integer> count = countCards()
        .values()
        .stream()
        .sorted()
        .toList();

    return getKeysByValueT(TYPES, count);
  }

  @Override
  public int compareTo(Hand hand) {
    if (this.getType().equals(hand.getType())) {
      for (int i = 0; i < this.cards().size(); i++) {
        final int compareValue = this.cards.get(i).getValue().compareTo(hand.cards().get(i).getValue());
        if (compareValue != 0) {
          return compareValue;
        }
      }
      return 0;
    }
    return RANK.get(this.getType()).compareTo(RANK.get(hand.getType()));
  }

  private Map<Card, Integer> countCards() {
    return this.cards.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(c -> 1)));
  }

  private static <T, E> T getKeysByValueT(Map<T, E> map, E value) {
    return map.entrySet()
        .stream()
        .filter(entry -> Objects.equals(entry.getValue(), value))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElseThrow(TypeDoesNotExistException::new);
  }

}
