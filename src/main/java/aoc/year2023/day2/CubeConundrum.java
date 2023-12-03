package aoc.year2023.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CubeConundrum {

  private static final Map<String, Integer> GAME_CUBE_BAG = Map.of(
      "red", 12,
      "green", 13,
      "blue", 14
  );

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2023/day2/game-notes.txt";
    final List<Note> notes = getNotes(path);

    final int sumOfIds = countValidGames(GAME_CUBE_BAG, notes);
    System.out.println(sumOfIds);

    final long sumOfCubePowers = sumOfPowerOfSets(notes);
    System.out.println(sumOfCubePowers);
  }

  static int sumOfPowerOfSets(List<Note> notes) {
    return notes.stream()
        .map(CubeConundrum::countMinimumRequiredCubes)
        .map(note -> note.subsetCount()
            .getFirst()
            .values()
            .stream()
            .reduce(0, Math::multiplyExact))
        .reduce(0, Integer::sum);
  }

  static int countValidGames(Map<String, Integer> gameCubeBag, List<Note> notes) {
    return notes.stream()
        .filter(note -> isPossibleGame(gameCubeBag, note))
        .mapToInt(Note::gameId)
        .sum();
  }

  private static Note countMinimumRequiredCubes(Note note) {
    final Map<String, Integer> minimumRequiredCubes = new HashMap<>();
    note.subsetCount()
        .stream()
        .map(Map::entrySet)
        .flatMap(Set::stream)
        .forEach(entry -> minimumRequiredCubes.merge(entry.getKey(), entry.getValue(), Math::max));

    return new Note(note.gameId(), List.of(minimumRequiredCubes));
  }

  private static boolean isPossibleGame(Map<String, Integer> gameCubeBag, Note note) {
    return note.subsetCount()
        .stream()
        .allMatch(map -> map.entrySet()
            .stream()
            .allMatch(entry -> gameCubeBag.get(entry.getKey()) >= entry.getValue()));
  }

  private static List<Note> getNotes(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.map(CubeConundrum::splitNotes)
          .map(note -> new Note(getGameNumber(note.getFirst()), countCubes(note.getLast())))
          .toList();
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private static List<String> splitNotes(String line) {
    return Arrays.stream(line.split(":"))
        .toList();
  }

  private static int getGameNumber(String game) {
    return Integer.parseInt(game.split(" ")[1]);
  }

  private static List<Map<String, Integer>> countCubes(String subsetNotes) {
    return Arrays.stream(subsetNotes.split(";"))
        .map(CubeConundrum::parseSubsets)
        .map(CubeConundrum::countColors)
        .toList();
  }

  private static List<String> parseSubsets(String subsets) {
    return Arrays.stream(subsets.split(","))
        .map(String::trim)
        .toList();
  }

  private static Map<String, Integer> countColors(List<String> color) {
    return color.stream()
        .map(c -> c.split(" "))
        .collect(Collectors.toMap(a -> a[1], a -> Integer.parseInt(a[0])));
  }

}
