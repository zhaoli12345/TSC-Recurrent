package com.cdut.current.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

    public static String regex = "\\$\\{([^}]+)}";

    public static String regex_exp = "-?\\d+(\\.\\d+)?";

    public static List<Long> getIdsFromFormula(String formula) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(formula);

        // 定义替换数字列表
        List<Long> ids = new ArrayList<>();

        // 替换匹配项
        while (matcher.find()) {
            String match = matcher.group(1);
            ids.add(Long.parseLong(match));
        }

        return ids;
    }

    /**
     * 根据公式计算年龄
     *
     * @param agesMap id-> age
     * @param formula ${1}-0.9*(${1}-${2})
     * @return 最终age
     */
    public static float calculateAge(Map<Long, Float> agesMap, String formula) {
        String infixExpression = changeFormula(agesMap, formula);
        return calculateInfixExpression(infixExpression);
    }

    /**
     * 将公式${1}-0.9*(${1}-${2}) 转成  5.335-0.9*(5.335-2.58)
     *
     * @param agesMap id-> age
     * @param formula 公式${1}-0.9*(${1}-${2})
     */
    public static String changeFormula(Map<Long, Float> agesMap, String formula) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(formula);

        // 替换匹配项
        StringBuilder expression = new StringBuilder();
        while (matcher.find()) {
            String match = matcher.group(1);
            Float age = agesMap.get(Long.parseLong(match));
            matcher.appendReplacement(expression, age.toString());
        }
        matcher.appendTail(expression);
        return expression.toString();
    }

    /**
     * 计算公式 例：5.335-0.9*(5.335-2.58)
     *
     * @param infixExpression 公式 例：5.335-0.9*(5.335-2.58)
     * @return 表达式的值age
     */
    public static float calculateInfixExpression(String infixExpression) {
        String expression = infixToPostfix(infixExpression);
        String[] tokens = expression.split(" ");
        Stack<BigDecimal> stack = new Stack<>();

        for (String token : tokens) {
            if (token.matches(regex_exp)) {
                stack.push(new BigDecimal(token));
            } else {
                BigDecimal num2 = stack.pop();
                BigDecimal num1 = stack.pop();
                BigDecimal result = BigDecimal.ZERO;
                switch (token) {
                    case "+":
                        result = num1.add(num2);
                        break;
                    case "-":
                        result = num1.subtract(num2);
                        break;
                    case "*":
                        result = num1.multiply(num2);
                        break;
                    case "/":
                        result = num1.divide(num2, 5, BigDecimal.ROUND_HALF_UP); // 保留5位小数
                        break;
                }
                stack.push(result);
            }
        }
        return stack.pop().floatValue();
    }

    /**
     * 中缀表达式->后缀表达式
     *
     * @param expression 例：5.335-0.9*(5.335-2.58)
     * @return 例：5.335 0.9 5.335 2.58 - * -
     */
    private static String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            if (Character.isDigit(ch) || ch == '.') {
                result.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(' ').append(stack.pop());
                }
                stack.pop();
            } else {
                result.append(' ');
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            result.append(' ').append(stack.pop());
        }

        return result.toString().trim();
    }

    private static int precedence(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }
}
