package aoc.year2023.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class WaitForIt {

  public static void main(String... args) {
    final String path = "src/main/java/aoc/year2023/day6/time-sheet.txt";
    final List<Race> races = importRaces(path);
    final long productOfRaceWins = productOfWinningRaces(races);
    System.out.println(productOfRaceWins);

    final Race race = importRace(path);
    final long numberOfWins = runRace(race);
    System.out.println(numberOfWins);
  }

  public static long productOfWinningRaces(List<Race> races) {
    return races.stream()
        .map(WaitForIt::runRace)
        .filter(i -> i > 0)
        .reduce(1L, Math::multiplyExact);
  }

  public static long runRace(Race race) {
    return isRaceTimeEven(race) ? countWins(race) - 1 : countWins(race);
  }

  private static long countWins(Race race) {
    return LongStream.rangeClosed(1, race.time() / 2)
        .map(i -> i * (race.time() - i))
        .filter(distance -> distance > race.distance())
        .count() * 2;
  }

  private static boolean isRaceTimeEven(Race race) {
    return Math.floorMod(race.time(), 2) == 0;
  }

  private static Race importRace(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      final List<String> data = stream.toList();
      final String time = Arrays.stream(data.getFirst()
              .split(":"))
          .skip(1)
          .map(s -> s.replaceAll("\\s+", ""))
          .collect(Collectors.joining());

      final String distance = Arrays.stream(data.getLast()
              .split(":"))
          .skip(1)
          .map(s -> s.replaceAll("\\s+", ""))
          .collect(Collectors.joining());

      return new Race(Long.parseLong(time), Long.parseLong(distance));
    } catch (IOException e) {
      return new Race(0, 0);
    }
  }

  private static List<Race> importRaces(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      final List<String> data = stream.toList();
      final List<Integer> times = Arrays.stream(data.getFirst()
              .split(":"))
          .skip(1)
          .map(s -> Arrays.stream(s.split("\\s+"))
              .filter(Predicate.not(String::isBlank))
              .map(Integer::parseInt)
              .toList())
          .flatMap(List::stream)
          .toList();

      final List<Integer> distances = Arrays.stream(data.getLast()
              .split(":"))
          .skip(1)
          .map(s -> Arrays.stream(s.split("\\s+"))
              .filter(Predicate.not(String::isBlank))
              .map(Integer::parseInt)
              .toList())
          .flatMap(List::stream)
          .toList();

      return IntStream.range(0, distances.size())
          .mapToObj(i -> new Race(times.get(i), distances.get(i)))
          .toList();
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }

}
