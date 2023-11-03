package aoc.year2022.day2;

import static aoc.year2022.day2.Hand.PAPER;
import static aoc.year2022.day2.Hand.ROCK;
import static aoc.year2022.day2.Hand.SCISSORS;

import java.util.Map;

record Game(Hand opponent, Hand mine) {

  static final Map<Character, Hand> OPPONENT_VALUES = Map.of(
      'A', ROCK,
      'B', PAPER,
      'C', SCISSORS
  );

  static final Map<Character, Hand> MY_VALUES = Map.of(
      'X', ROCK,
      'Y', PAPER,
      'Z', SCISSORS
  );

  static final Map<Hand, Hand> WIN = Map.of(
      ROCK, PAPER,
      PAPER, SCISSORS,
      SCISSORS, ROCK
  );

  static final Map<Hand, Hand> LOSE = Map.of(
      ROCK, SCISSORS,
      PAPER, ROCK,
      SCISSORS, PAPER
  );

  static final Map<Hand, Hand> DRAW = Map.of(
      ROCK, ROCK,
      PAPER, PAPER,
      SCISSORS, SCISSORS
  );

}
