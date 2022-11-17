package aoc.year2021.day16;

import java.util.StringJoiner;

public final class LiteralValue extends Packet {

  private final long value;

  public LiteralValue(int version, int typeId, long value) {
    super(version, typeId);
    this.value = value;
  }

  @Override
  public int sumVersions() {
    return version;
  }

  @Override
  public long getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LiteralValue that = (LiteralValue) o;

    return value == that.value;
  }

  @Override
  public int hashCode() {
    return (int) (value ^ (value >>> 32));
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", LiteralValue.class.getSimpleName() + "[", "]")
        .add("version=" + version)
        .add("typeId=" + typeId)
        .add("value=" + value)
        .toString();
  }

}
