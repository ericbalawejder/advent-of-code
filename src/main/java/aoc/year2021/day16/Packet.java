package aoc.year2021.day16;

public abstract sealed class Packet permits LiteralValue, Operator {

  protected final int version;
  protected final int typeId;

  public Packet(int version, int typeId) {
    this.version = version;
    this.typeId = typeId;
  }

  public abstract int sumVersions();

  public abstract long getValue();

}
