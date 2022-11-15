package aoc.year2021.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class GiantSquid {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day4/random-set-of-boards.txt";
    final List<Integer> gameNumbers = getGameNumbers(path);
    final List<GameCard> gameCards = getGameCards(path);
    System.out.println(playBingo(gameCards, gameNumbers));
    System.out.println(findLastCardToBingo(gameCards, gameNumbers));
  }

  static OptionalInt findLastCardToBingo(List<GameCard> gameCards, List<Integer> numbersToCall) {
    final List<GameCard> winningCards = new ArrayList<>(gameCards.size());
    for (int number : numbersToCall) {
      for (GameCard card : gameCards) {
        if (isAWin(card, number)) {
          winningCards.add(card);
          final List<GameCard> cards = difference(gameCards, winningCards);
          if (cards.size() == 1 && isAWin(cards.get(0), number)) {
            return OptionalInt.of(computeScore(cards.get(0), number));
          }
        }
      }
    }
    return OptionalInt.empty();
  }

  static Optional<Integer> playBingo(List<GameCard> gameCards, List<Integer> numbersToCall) {
    return numbersToCall.stream()
        .map(number -> gameCards.stream()
            .filter(card -> isAWin(card, number))
            .map(card -> computeScore(card, number))
            .findFirst())
        .flatMap(Optional::stream)
        .findAny();
  }

  private static int computeScore(GameCard gameCard, int winningNumber) {
    return gameCard.sumUnmarkedCardNumbers() * winningNumber;
  }

  private static boolean isAWin(GameCard gameCard, int number) {
    gameCard.markCardNumber(number, -1);
    final int[][] card = gameCard.getGrid();
    final boolean rowCheck = Arrays.stream(card)
        .anyMatch(row -> Arrays.stream(row).allMatch(n -> n == -1));

    final boolean columnCheck = Arrays.stream(gameCard.transpose(card))
        .anyMatch(column -> Arrays.stream(column).allMatch(n -> n == -1));

    return rowCheck || columnCheck;
  }

  private static <T> List<T> difference(final List<T> listA, final List<T> listB) {
    return listA.stream()
        .filter(e -> !listB.contains(e))
        .toList();
  }

  private static List<GameCard> getGameCards(String path) {
    try {
      final String data = Files.readString(Path.of(path));
      return Arrays.stream(data.split("\n\n"))
          .skip(1)
          .map(GameCard::new)
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static List<Integer> getGameNumbers(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.findFirst()
          .map(s -> Arrays.stream(s.split(","))
              .map(Integer::parseInt)
              .toList())
          .orElse(Collections.emptyList());
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
