package aoc.year2024.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class CeresSearch {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2024/day4/word-search.txt";
    final Map<Coordinate, Character> wordSearchPuzzle = readPuzzle(path);

    final int wordCount = countWords(wordSearchPuzzle, "xmas");
    System.out.println(wordCount);

    final int wordCountXMAS = countXMAS(wordSearchPuzzle, "mas");
    System.out.println(wordCountXMAS);
  }

  static int countXMAS(Map<Coordinate, Character> wordSearchPuzzle, String word) {
    return wordSearchPuzzle.entrySet()
        .stream()
        .filter(entry -> entry.getValue().equals(word.toUpperCase().charAt(1)))
        .map(entry -> entry.getKey().xPaths())
        .filter(path -> isPathMAS(path, wordSearchPuzzle, word.toUpperCase()))
        .mapToInt(e -> 1)
        .reduce(0, Integer::sum);
  }

  static int countWords(Map<Coordinate, Character> wordSearchPuzzle, String word) {
    return wordSearchPuzzle.entrySet()
        .stream()
        .filter(entry -> entry.getValue().equals(word.toUpperCase().charAt(0)))
        .map(entry -> entry.getKey().adjacentPaths())
        .flatMap(List::stream)
        .filter(path -> isPathXMAS(path, wordSearchPuzzle, word.toUpperCase()))
        .mapToInt(e -> 1)
        .reduce(0, Integer::sum);
  }

  private static boolean isPathMAS(List<Path> xPaths, Map<Coordinate, Character> wordSearchPuzzle, String word) {
    final Set<String> words = Set.of(word, new StringBuilder(word).reverse().toString());

    final String x1 = xPaths.getFirst()
        .coordinates()
        .stream()
        .map(wordSearchPuzzle::get)
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
        .toString();

    final String x2 = xPaths.getLast()
        .coordinates()
        .stream()
        .map(wordSearchPuzzle::get)
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
        .toString();

    return words.contains(x1) && words.contains(x2);
  }

  private static boolean isPathXMAS(Path path, Map<Coordinate, Character> wordSearchPuzzle, String word) {
    return path.coordinates()
        .stream()
        .map(wordSearchPuzzle::get)
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
        .toString()
        .equals(word);
  }

  private static Map<Coordinate, Character> readPuzzle(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      final List<String> lines = stream.toList();
      final Map<Coordinate, Character> puzzle = new LinkedHashMap<>();
      for (int y = 0; y < lines.size(); y++) {
        final String line = lines.get(y);
        for (int x = 0; x < line.length(); x++) {
          puzzle.put(new Coordinate(x, y), line.charAt(x));
        }
      }
      return Collections.unmodifiableMap(puzzle);
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyMap();
    }
  }

}
