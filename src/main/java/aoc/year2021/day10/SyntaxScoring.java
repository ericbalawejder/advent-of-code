package aoc.year2021.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class SyntaxScoring {

  private final static Map<Character, Integer> CHARACTER_SCORE = Map.of(
      ')', 3,
      ']', 57,
      '}', 1197,
      '>', 25137,
      '0', 0);

  private final static Map<Character, Integer> COMPLETION_SCORE = Map.of(
      ')', 1,
      ']', 2,
      '}', 3,
      '>', 4
  );

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day10/navigation-subsystem.txt";
    final List<String> chunks = getChunks(path);
    System.out.println(computeSyntaxErrorScore(chunks));
    System.out.println(computeAutocompleteScore(chunks));
  }

  static int computeSyntaxErrorScore(List<String> lines) {
    return lines.stream()
        .map(SyntaxScoring::getFirstIllegalCharacter)
        .map(CHARACTER_SCORE::get)
        .reduce(0, Integer::sum);
  }

  static long computeAutocompleteScore(List<String> lines) {
    final List<Long> scores = findClosingCharacters(lines)
        .stream()
        .map(SyntaxScoring::computeScore)
        .sorted()
        .toList();

    return scores.get(scores.size() / 2);
  }

  private static long computeScore(String syntax) {
    return syntax.chars()
        .mapToObj(c -> (char) c)
        .mapToLong(COMPLETION_SCORE::get)
        .reduce(0L, SyntaxScoring::rule);
  }

  private static long rule(long s, long e) {
    final BiFunction<Long, Long, Long> compute = (subtotal, element) -> 5 * subtotal + element;
    return compute.apply(s, e);
  }

  private static List<String> findClosingCharacters(List<String> lines) {
    return lines.stream()
        .filter(l -> getFirstIllegalCharacter(l) == '0')
        .map(SyntaxScoring::getRemainder)
        .toList();
  }

  private static char getFirstIllegalCharacter(String line) {
    final Deque<Character> stack = new ArrayDeque<>();
    char illegalCharacter = '0';
    for (int i = 0; i < line.length() && illegalCharacter == '0'; i++) {
      final char character = line.charAt(i);
      illegalCharacter = switch (character) {
        case '[', '(', '{', '<' -> {
          stack.push(character);
          yield '0';
        }
        case ']' -> stack.pop() != '[' ? character : '0';
        case ')' -> stack.pop() != '(' ? character : '0';
        case '}' -> stack.pop() != '{' ? character : '0';
        case '>' -> stack.pop() != '<' ? character : '0';
        default -> '0';
      };
    }
    return illegalCharacter;
  }

  private static String getRemainder(String line) {
    final Deque<Character> stack = new ArrayDeque<>();
    for (int i = 0; i < line.length(); i++) {
      final char character = line.charAt(i);
      switch (character) {
        case '[', '(', '{', '<' -> stack.push(character);
        case ']', ')', '}', '>' -> stack.pop();
      }
    }
    final StringBuilder closingCharacters = new StringBuilder();
    while (!stack.isEmpty()) {
      final String remainder = switch (stack.pop()) {
        case '[' -> "]";
        case '(' -> ")";
        case '{' -> "}";
        case '<' -> ">";
        default -> "";
      };
      closingCharacters.append(remainder);
    }
    return closingCharacters.toString();
  }

  private static List<String> getChunks(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
