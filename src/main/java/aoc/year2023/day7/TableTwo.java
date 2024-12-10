package aoc.year2023.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TableTwo {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2023/day7/camel-cards.txt";
    final List<CamelCardTwo> camelCards = importCamelCards(path);
    final int totalWinnings = computeWinnings(camelCards);
    System.out.println(totalWinnings);
  }

  static int computeWinnings(List<CamelCardTwo> camelCards) {
    return IntStream.rangeClosed(1, camelCards.size())
        .map(rank -> rank * camelCards.get(rank - 1).bid())
        .sum();
  }

  private static List<CamelCardTwo> importCamelCards(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(line -> line.split(" "))
          .map(a -> new CamelCardTwo(new HandTwo(getCards(a[0])), Integer.parseInt(a[1])))
          .sorted(Comparator.comparing(CamelCardTwo::hand))
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static List<CardTwo> getCards(String sequence) {
    return sequence.chars()
        .mapToObj(c -> (char) c)
        .map(CardTwo::new)
        .toList();
  }

}
