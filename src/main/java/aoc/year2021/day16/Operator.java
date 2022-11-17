package aoc.year2021.day16;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public final class Operator extends Packet {

  private final List<Packet> subPackets;

  public Operator(int version, int typeId, List<Packet> subPackets) {
    super(version, typeId);
    this.subPackets = subPackets;
  }

  @Override
  public int sumVersions() {
    return subPackets.stream()
        .mapToInt(Packet::sumVersions)
        .sum() + version;
  }

  @Override
  public long getValue() {
    return switch (typeId) {
      case 0 -> subPackets.stream()
          .mapToLong(Packet::getValue)
          .sum();
      case 1 -> subPackets.stream()
          .mapToLong(Packet::getValue)
          .reduce(1L, Math::multiplyExact);
      case 2 -> subPackets.stream()
          .mapToLong(Packet::getValue)
          .min()
          .orElseThrow();
      case 3 -> subPackets.stream()
          .mapToLong(Packet::getValue)
          .max()
          .orElseThrow();
      case 5 -> subPackets.get(0).getValue() > subPackets.get(1).getValue() ? 1L : 0L;
      case 6 -> subPackets.get(0).getValue() < subPackets.get(1).getValue() ? 1L : 0L;
      case 7 -> subPackets.get(0).getValue() == subPackets.get(1).getValue() ? 1L : 0L;
      default -> throw new IllegalStateException("unknown typeID: " + typeId);
    };
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Operator operator = (Operator) o;

    return Objects.equals(subPackets, operator.subPackets);
  }

  @Override
  public int hashCode() {
    return subPackets != null ? subPackets.hashCode() : 0;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Operator.class.getSimpleName() + "[", "]")
        .add("version=" + version)
        .add("typeId=" + typeId)
        .add("subPackets=" + subPackets)
        .toString();
  }

}
