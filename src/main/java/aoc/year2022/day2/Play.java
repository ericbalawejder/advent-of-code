package aoc.year2022.day2;

import java.util.Arrays;

enum Play {

  WIN('Z'),
  LOSE('X'),
  DRAW('Y');

  private final char response;

  Play(char response) {
    this.response = response;
  }

  char getResponse() {
    return response;
  }

  static Play getOutcome(Character response) {
    return Arrays.stream(values())
        .filter(play -> play.response == response)
        .findFirst()
        .orElseThrow();
  }

}
