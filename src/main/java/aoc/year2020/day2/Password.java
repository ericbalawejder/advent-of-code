package aoc.year2020.day2;

import java.util.Objects;

public class Password {

  private final int min;
  private final int max;
  private final char character;
  private final String text;

  public Password(String policy) {
    final String[] tuple = policy.split(" ");
    final String[] range = tuple[0].split("-");
    this.character = tuple[1].charAt(0);
    this.text = tuple[2];
    this.min = Integer.parseInt(range[0]);
    this.max = Integer.parseInt(range[1]);
  }

  public int getMin() {
    return min;
  }

  public int getMax() {
    return max;
  }

  public char getCharacter() {
    return character;
  }

  public String getText() {
    return text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Password password1 = (Password) o;
    return min == password1.min &&
        max == password1.max &&
        character == password1.character &&
        text.equals(password1.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(min, max, character, text);
  }

  @Override
  public String toString() {
    return "Password{" +
        "min=" + min +
        ", max=" + max +
        ", character=" + character +
        ", password='" + text + '\'' +
        '}';
  }

}
