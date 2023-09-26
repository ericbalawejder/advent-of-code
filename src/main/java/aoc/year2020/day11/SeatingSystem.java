package aoc.year2020.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SeatingSystem {

  public static void main(String[] args) throws Exception {
    final SeatingSystem seatingSystem = new SeatingSystem();
    final List<String> seatingLayout = seatingSystem.readFile("src/main/java/aoc/year2020/day11/seat-layout.txt");
    final Grid grid = seatingSystem.createGrid(seatingLayout);

    System.out.println(seatingSystem.modelSeatsPart1(grid));
    System.out.println(seatingSystem.modelSeatsPart2(grid));
  }

  int modelSeatsPart1(Grid grid) {
    while (true) {
      final Grid next = grid.getNextSeatingLayout();
      if (grid.equals(next)) {
        return grid.countSeats(Status.OCCUPIED);
      }
      grid = next;
    }
  }

  int modelSeatsPart2(Grid grid) {
    while (true) {
      final Grid next = grid.getNextSeatingLayoutVisibility();
      if (grid.equals(next)) {
        return grid.countSeats(Status.OCCUPIED);
      }
      grid = next;
    }
  }

  Grid createGrid(List<String> seatingLayout) {
    final Grid grid = new Grid(seatingLayout.size(), seatingLayout.get(0).length());
    for (int i = 0; i < seatingLayout.size(); i++) {
      for (int j = 0; j < seatingLayout.get(i).length(); j++) {
        if (seatingLayout.get(i).charAt(j) == 'L') {
          grid.setStatus(i, j, Status.EMPTY);
        } else if (seatingLayout.get(i).charAt(j) == '#') {
          grid.setStatus(i, j, Status.OCCUPIED);
        } else {
          grid.setStatus(i, j, Status.FLOOR);
        }
      }
    }
    return grid;
  }

  private List<String> readFile(String path) {
    try (Stream<String> stream = Files.lines(Paths.get(path))) {
      return stream.toList();
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

}
