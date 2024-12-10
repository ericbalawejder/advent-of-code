package aoc.year2023.day7;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static aoc.year2023.day7.Type.FIVE_OF_A_KIND;
import static aoc.year2023.day7.Type.FOUR_OF_A_KIND;
import static aoc.year2023.day7.Type.FULL_HOUSE;
import static aoc.year2023.day7.Type.HIGH_CARD;
import static aoc.year2023.day7.Type.ONE_PAIR;
import static aoc.year2023.day7.Type.THREE_OF_A_KIND;
import static aoc.year2023.day7.Type.TWO_PAIR;

record HandTwo(List<CardTwo> cards) implements Comparable<HandTwo> {

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
    if (containsAJoker()) {
      return determineJokerHandType();
    }
    final List<Integer> count = countCards()
        .values()
        .stream()
        .sorted()
        .toList();

    return getKeysByValue(TYPES, count);
  }

  @Override
  public int compareTo(HandTwo hand) {
    if (this.getType().equals(hand.getType())) {
      return IntStream.range(0, this.cards().size())
          .map(i -> this.cards.get(i).getValue().compareTo(hand.cards().get(i).getValue()))
          .filter(i -> i != 0)
          .findFirst()
          .orElse(0);
    }
    return RANK.get(this.getType()).compareTo(RANK.get(hand.getType()));
  }

  private Map<CardTwo, Integer> countCards() {
    if (cards == null || cards.isEmpty()) {
      return Collections.emptyMap();
    }
    return this.cards.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(c -> 1)));
  }

  private static <T, E> T getKeysByValue(Map<T, E> map, E value) {
    return map.entrySet()
        .stream()
        .filter(entry -> Objects.equals(entry.getValue(), value))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElseThrow(TypeDoesNotExistException::new);
  }

  private Type determineJokerHandType() {
    final List<CardTwo> nonJokers = this.cards.stream()
        .filter(card -> !card.isAJoker())
        .toList();

    if (nonJokers.isEmpty()) {
      return FIVE_OF_A_KIND;
    }
    final Map<CardTwo, Integer> cardCount = nonJokers.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(c -> 1)));

    final Map<CardTwo, Integer> sortedCardCount = cardCount.entrySet()
        .stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    final Map.Entry<CardTwo, Integer> mostFrequentCard = sortedCardCount.entrySet()
        .stream()
        .findFirst()
        .orElseThrow();

    final int numberOfJokers = 5 - nonJokers.size();
    sortedCardCount.compute(mostFrequentCard.getKey(), (k, v) -> v + numberOfJokers);

    final List<Integer> counts = sortedCardCount.values()
        .stream()
        .sorted()
        .toList();

    return getKeysByValue(TYPES, counts);
  }

  private boolean containsAJoker() {
    return this.cards.stream()
        .anyMatch(CardTwo::isAJoker);
  }

}
