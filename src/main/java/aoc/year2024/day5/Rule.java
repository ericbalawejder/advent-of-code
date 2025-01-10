package aoc.year2024.day5;

record Rule(int x, int y) {

  @Override
  public String toString() {
    return x + " | " + y;
  }

}
