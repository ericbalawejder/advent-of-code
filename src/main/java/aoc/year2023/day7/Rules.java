package aoc.year2023.day7;

import java.util.List;
import java.util.Map;

import static aoc.year2023.day7.Type.FIVE_OF_A_KIND;
import static aoc.year2023.day7.Type.FOUR_OF_A_KIND;
import static aoc.year2023.day7.Type.FULL_HOUSE;
import static aoc.year2023.day7.Type.HIGH_CARD;
import static aoc.year2023.day7.Type.ONE_PAIR;
import static aoc.year2023.day7.Type.THREE_OF_A_KIND;
import static aoc.year2023.day7.Type.TWO_PAIR;

class Rules {

  private static final Map<Type, List<Integer>> HANDS = Map.of(
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

  private static final Map<Character, Integer> CARD_ONE = Map.ofEntries(
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

  private static final Map<Character, Integer> CARD_TWO = Map.ofEntries(
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

}
