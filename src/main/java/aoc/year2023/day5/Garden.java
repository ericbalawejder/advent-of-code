package aoc.year2023.day5;

import java.util.List;
import java.util.Optional;

record Garden(String name, List<Range> ranges) {

  long compute(long seed) {
    return ranges.stream()
        .map(range -> mapSeed(seed, range))
        .flatMap(Optional::stream)
        .findAny()
        .orElse(seed);
  }

  private Optional<Long> mapSeed(long seed, Range range) {
    return seed >= range.sourceStart() && seed < range.sourceStart() + range.rangeLength() ?
        Optional.of(seed - range.sourceStart() + range.destinationStart()) : Optional.empty();
  }

}
