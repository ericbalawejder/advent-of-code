package aoc.year2022.day2;

enum Hand {

  ROCK(1),
  PAPER(2),
  SCISSORS(3);

  private final int value;

  Hand(int value) {
    this.value = value;
  }

  int getValue() {
    return value;
  }

}
