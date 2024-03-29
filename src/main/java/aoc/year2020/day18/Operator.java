package aoc.year2020.day18;

/**
 * Normal arithmetic rules.
 * ADD(1, "+"),
 * SUBTRACT(2, "-"),
 * MULTIPLY(3, "*"),
 * DIVIDE(4, "/"),
 * EXPONENT(5, "^"),
 * ABS(6, "abs");
 * ---------------------------
 * Part 1: precedence is equal
 * ADD(1, "+"),
 * MULTIPLY(1, "*");
 * <p>
 * Part 2: precedence addition > multiplication
 * ADD(1, "+"),
 * MULTIPLY(0, "*");
 */

public enum Operator {
  ADD(1, "+"),
  MULTIPLY(0, "*");

  final int precedence;
  final String sign;

  Operator(int precedence, String sign) {
    this.precedence = precedence;
    this.sign = sign;
  }

}
