package aoc.day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperationOrder {

    public static void main(String[] args) {
        final List<String> homework = readFile("src/main/java/aoc/day18/test.txt");
        System.out.println(evaluateHomework(homework));
    }

    static long evaluateHomework(List<String> homework) {
        return homework.stream()
                .map(OperationOrder::evaluateExpression)
                .reduce(0L, Math::addExact);
    }

    private static long evaluateExpression(String expression) {
        while (expression.contains("(")) {
            expression = processParentheses(expression);
        }
        return Long.parseLong(evaluateExpressionInOrder(expression));
    }

    private static String processParentheses(String expression) {
        final int closingParenthesis = expression.indexOf(")");
        final int openParenthesis = expression.lastIndexOf("(", closingParenthesis);

        return expression.substring(0, openParenthesis)
                + evaluateExpressionInOrder(expression.substring(openParenthesis + 1, closingParenthesis))
                + expression.substring(closingParenthesis + 1);
    }

    private static String evaluateExpressionInOrder(String expression) {
        String[] components = expression.split(" ");
        long result = Long.parseLong(components[0]);
        String operation = "";
        for (int i = 1; i < components.length; i++) {
            if (components[i].equals("*")) {
                operation = "mult";
            } else if (components[i].equals("+")) {
                operation = "add";
            } else {
                if (operation.equals("mult")) {
                    result *= Long.parseLong(components[i]);
                } else if (operation.equals("add")) {
                    result += Long.parseLong(components[i]);
                }
            }
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
