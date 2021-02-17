package aoc.day18;

/**
 * Normal arithmetic rules.
 *
 *     ADD(1, "+"),
 *     SUBTRACT(2, "-"),
 *     MULTIPLY(3, "*"),
 *     DIVIDE(4, "/"),
 *     EXPONENT(5, "^");
 *     //ABS(?, |)
 */

public enum Operator {
    ADD(1, "+"),
    MULTIPLY(1, "*");

    final int precedence;
    final String sign;

    Operator(int precedence, String sign) {
        this.precedence = precedence;
        this.sign = sign;
    }

}