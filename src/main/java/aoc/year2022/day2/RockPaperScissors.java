package aoc.year2022.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RockPaperScissors {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2022/day2/encrypted-strategy-guide.txt";
    final List<Game> games = readStrategyGuide(path);
    final int score = scoreTournament(games);
    System.out.println(score);

    final List<Instruction> instructions = readStrategyGuideCorrectly(path);
    final List<Game> correctGames = generateHands(instructions);
    final int score2 = scoreTournament(correctGames);
    System.out.println(score2);
  }

  static int scoreTournament(List<Game> games) {
    return games.stream()
        .map(RockPaperScissors::scoreGame)
        .reduce(0, Integer::sum);
  }

  static List<Game> generateHands(List<Instruction> instructions) {
    return instructions.stream()
        .map(RockPaperScissors::pickHand)
        .collect(Collectors.toList());
  }

  private static Game pickHand(Instruction guide) {
    final Play play = Play.getOutcome(guide.outcome());
    final Hand hand = guide.hand();
    final Hand handToPlay = switch (play) {
      case WIN:
        yield Game.WIN.get(hand);
      case LOSE:
        yield Game.LOSE.get(hand);
      case DRAW:
        yield Game.DRAW.get(hand);
    };
    return new Game(hand, handToPlay);
  }

  private static int scoreGame(Game game) {
    final Hand opponentValue = game.opponent();
    final Hand myValue = game.mine();
    int score = 0;
    if (opponentValue.equals(myValue)) {
      score = myValue.getValue() + 3;
    } else if (opponentValue.equals(Hand.ROCK) && myValue.equals(Hand.PAPER)) {
      score = myValue.getValue() + 6;
    } else if (opponentValue.equals(Hand.ROCK) && myValue.equals(Hand.SCISSORS)) {
      score = myValue.getValue();
    } else if (opponentValue.equals(Hand.PAPER) && myValue.equals(Hand.ROCK)) {
      score = myValue.getValue();
    } else if (opponentValue.equals(Hand.PAPER) && myValue.equals(Hand.SCISSORS)) {
      score = myValue.getValue() + 6;
    } else if (opponentValue.equals(Hand.SCISSORS) && myValue.equals(Hand.ROCK)) {
      score = myValue.getValue() + 6;
    } else if (opponentValue.equals(Hand.SCISSORS) && myValue.equals(Hand.PAPER)) {
      score = myValue.getValue();
    }
    return score;
  }

  private static List<Instruction> readStrategyGuideCorrectly(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(s -> s.split(" "))
          .map(a -> new Instruction(Game.OPPONENT_VALUES.get(a[0].charAt(0)), a[1].charAt(0)))
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static List<Game> readStrategyGuide(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(s -> s.split(" "))
          .map(a -> new Game(
              Game.OPPONENT_VALUES.get(a[0].charAt(0)),
              Game.MY_VALUES.get(a[1].charAt(0))))
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
