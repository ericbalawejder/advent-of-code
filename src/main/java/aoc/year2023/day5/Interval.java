package aoc.year2023.day5;

record Interval(long a, long b) implements Comparable<Interval> {

  Interval {
    if (b <= a) {
      throw new IllegalArgumentException("interval must be strictly increasing");
    }
  }

  long distance() {
    return Math.abs(b - a);
  }

  @Override
  public int compareTo(Interval o) {
    return Long.compare(this.a(), o.a());
  }

}
