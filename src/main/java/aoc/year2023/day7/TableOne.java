package aoc.year2023.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class TableOne {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2023/day7/camel-cards.txt";
    final List<CamelCardOne> camelCards = importCamelCards(path);
    final int totalWinnings = computeWinnings(camelCards);
    System.out.println(totalWinnings);
  }

  static int computeWinnings(List<CamelCardOne> camelCards) {
    return IntStream.rangeClosed(1, camelCards.size())
        .map(rank -> rank * camelCards.get(rank - 1).bid())
        .sum();
  }

  private static List<CamelCardOne> importCamelCards(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(line -> line.split(" "))
          .map(a -> new CamelCardOne(new HandOne(getCards(a[0])), Integer.parseInt(a[1])))
          .sorted(Comparator.comparing(CamelCardOne::hand))
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static List<CardOne> getCards(String sequence) {
    return sequence.chars()
        .mapToObj(c -> (char) c)
        .map(CardOne::new)
        .toList();
  }

}
