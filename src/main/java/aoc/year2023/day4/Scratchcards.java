package aoc.year2023.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Scratchcards {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2023/day4/scratchcards.txt";
    final List<Card> cards = getCards(path);

    final int score = scoreCards(cards);
    System.out.println(score);

    final int count = scoreCardsWithNewRules(cards);
    System.out.println(count);
  }

  static int scoreCardsWithNewRules(List<Card> cards) {
    final Map<Card, Integer> cardCount = cards.stream()
        .collect(Collectors.toMap(Function.identity(), v -> 1));

    cards.forEach(card -> {
      final int numberOfWinningCards = intersection(card.winningNumbers(), card.picks()).size();
      final Integer count = cardCount.get(card);
      IntStream.iterate(card.cardId() + 1, i -> i <= card.cardId() + numberOfWinningCards, i -> i + 1)
          .forEach(i -> cardCount.merge(cards.get(i - 1), count, Math::addExact));
    });
    return cardCount.values()
        .stream()
        .reduce(0, Integer::sum);
  }

  static int scoreCards(List<Card> cards) {
    return cards.stream()
        .mapToInt(Scratchcards::scoreCard)
        .sum();
  }

  private static int scoreCard(Card card) {
    final Set<Integer> intersection = intersection(card.winningNumbers(), card.picks());
    return intersection.isEmpty() ? 0 : (int) Math.pow(2, intersection.size() - 1);
  }

  private static <T> Set<T> intersection(final Set<T> setA, final Set<T> setB) {
    return setA.stream()
        .filter(setB::contains)
        .collect(Collectors.toUnmodifiableSet());
  }

  private static List<Card> getCards(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(Scratchcards::processCards)
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static Card processCards(String line) {
    final String[] parts = line.split(":");
    final int cardNumber = getCardNumber(parts[0]);
    final List<Set<Integer>> numbers = parseNumbers(parts[1]);

    return new Card(cardNumber, numbers.getFirst(), numbers.getLast());
  }

  private static List<Set<Integer>> parseNumbers(String numbers) {
    return Arrays.stream(numbers.split("\\|"))
        .map(s -> Arrays.stream(s.split(" "))
            .filter(Predicate.not(String::isBlank))
            .map(Integer::parseInt)
            .collect(Collectors.toUnmodifiableSet()))
        .toList();
  }

  private static int getCardNumber(String card) {
    return Integer.parseInt(card.split("\\s+")[1]);
  }

}
