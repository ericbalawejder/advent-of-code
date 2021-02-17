package aoc.day18;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://codereview.stackexchange.com/questions/232483/calculator-using-shunting-yard-algorithm
public class ShuntingYard {

    private static final Map<String, Operator> OPERATOR_MAP = Map.of(
                                    Operator.ADD.sign, Operator.ADD,
                                    Operator.MULTIPLY.sign, Operator.MULTIPLY
    );

    public static void main(String[] args) {
        //final List<String> homework = readFile("src/main/java/aoc/day18/homework.txt");
        final List<String> homework = readFile("src/main/java/aoc/day18/test.txt");
        final String expression = "(9 * 6 + 2 * 6) * (6 * 6 + 2) * (7 * (3 + 6 * 8 * 6 * 5) * 8)";
        final String filtered = expression.replaceAll(" ", "");
        //System.out.println(evaluateExpression(filtered));
        System.out.println(evaluateHomework(homework));
    }

    static Long evaluateHomework(List<String> homework) {
        return homework.stream()
                .map(s -> s.replaceAll(" ", ""))
                .map(ShuntingYard::evaluateExpression)
                .reduce(0L, Long::sum);
    }

    private static Long evaluateExpression(String infix) {
        final Stack<String> operatorStack = new Stack<>();
        final Stack<Long> computationStack = new Stack<>();

        for (String currentSymbol : infix.split("")) {

            if ("(".equalsIgnoreCase(currentSymbol)) {
                operatorStack.push(currentSymbol);
            } else if (OPERATOR_MAP.containsKey(currentSymbol)) {
                while (!operatorStack.isEmpty() &&
                        isHighPrecedence(currentSymbol, operatorStack.peek())) {

                    String higherPrecedenceOperator = operatorStack.pop();
                    Long operandLeft = computationStack.pop();
                    Long operandRight = computationStack.pop();
                    computationStack.push(evaluate(operandLeft, operandRight, higherPrecedenceOperator));
                }
                operatorStack.push(currentSymbol);
            } else if (currentSymbol.equalsIgnoreCase(")")) {
                while (!"(".equalsIgnoreCase(operatorStack.peek())) {
                    String higherPrecedenceOperator = operatorStack.pop();
                    Long operandLeft = computationStack.pop();
                    Long operandRight = computationStack.pop();
                    computationStack.push(evaluate(operandLeft, operandRight, higherPrecedenceOperator));
                }
                operatorStack.pop();
            } else {
                computationStack.push(Long.valueOf(currentSymbol));
            }
        }
        while (!operatorStack.empty()) {
            String higherPrecedenceOperator = operatorStack.pop();
            Long operandRight = computationStack.pop();
            Long operandLeft = computationStack.pop();
            computationStack.push(evaluate(operandLeft, operandRight, higherPrecedenceOperator));
        }
        return computationStack.pop();
    }

    private static Long evaluate(Long operandLeft, Long operandRight, String operator) {
        final BigDecimal left = BigDecimal.valueOf(operandLeft);
        final BigDecimal right = BigDecimal.valueOf(operandRight);
        if (operator.equals(Operator.MULTIPLY.sign)) {
            return left.multiply(right).longValue();
        } else if (operator.equals(Operator.ADD.sign)) {
            return left.add(right).longValue();
        } else {
            throw new IllegalArgumentException("Invalid operator symbol.");
        }
    }

    private static boolean isHighPrecedence(String currentSymbol, String peekedOperator) {
        return OPERATOR_MAP.containsKey(peekedOperator) &&
                OPERATOR_MAP.get(peekedOperator).precedence >=
                        OPERATOR_MAP.get(currentSymbol).precedence;
    }

    private static List<String> readFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
