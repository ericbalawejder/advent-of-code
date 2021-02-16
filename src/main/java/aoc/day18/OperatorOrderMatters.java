package aoc.day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://en.wikipedia.org/wiki/Shunting-yard_algorithm
// http://andreinc.net/2010/10/05/converting-infix-to-rpn-shunting-yard-algorithm/
public class OperatorOrderMatters {

    public static void main(String[] args) {
        final List<String> homework = readFile("src/main/java/aoc/day18/ddddddddd.txt");
        System.out.println(evaluateHomework(homework));
    }

    static long evaluateHomework(List<String> homework) {
        return homework.stream()
                .map(OperatorOrderMatters::evaluateExpression)
                .reduce(0L, Math::addExact);
    }

    private static long evaluateExpression(String expression) {
        while (expression.contains("(")) {
            expression = processParentheses(expression);
        }
        return Long.parseLong(evaluateExpressionAdditionFirst(expression));
    }

    private static String processParentheses(String expression) {
        final int closingParenthesis = expression.indexOf(")");
        final int openParenthesis = expression.lastIndexOf("(", closingParenthesis);

        return expression.substring(0, openParenthesis)
                + evaluateExpressionAdditionFirst(expression.substring(openParenthesis + 1, closingParenthesis))
                + expression.substring(closingParenthesis + 1);
    }

    private static String evaluateExpressionAdditionFirst(String expression) {
        String[] components = expression.split(" ");
        List<String> terms = new ArrayList<>(Arrays.asList(components));

        while (terms.contains("+")) {
            int i = terms.indexOf("+");
            long num1 = Long.parseLong(terms.get(i - 1));
            long num2 = Long.parseLong(terms.get(i + 1));
            terms.set(i - 1, num1 + num2 + "");
            terms.remove(i);
            terms.remove(i);
        }
        long result = 1;
        for (int i = 0; i < terms.size(); i += 2) {
            result *= Long.parseLong(terms.get(i));
        }
        return String.valueOf(result);
    }

    private static List<String> readFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toUnmodifiableList());
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
