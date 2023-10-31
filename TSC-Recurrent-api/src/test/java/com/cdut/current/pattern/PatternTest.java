package com.cdut.current.pattern;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    @Test
    public void testPattern() {
        String str = "${1}-0.9*(${1}-${2})";

        // 定义正则表达式
        String regex = "\\$\\{([^}]+)}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        // 定义替换数字列表
        List<String> replacementList = new ArrayList<>();
        replacementList.add("5.335");
        replacementList.add("2.58");

        // 替换匹配项
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            String replacement = replacementList.get(Integer.parseInt(match)-1);
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);

        System.out.println(sb);
        Assert.assertTrue(true);
    }

    @Test
    public void testCalculate() {
        String infixExpression  = "5.335-0.9*(5.335-2.58)";
        System.out.println(infixExpression);
        String postfixExpression = infixToPostfix(infixExpression);
        System.out.println(postfixExpression);
        BigDecimal  result = calculate(postfixExpression);
        System.out.println("结果: " + result);
    }
    public int precedence(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    public String infixToPostfix(String expression) {
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

    public BigDecimal calculate(String expression) {
        String[] tokens = expression.split(" ");
        Stack<BigDecimal> stack = new Stack<>();

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
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
        return stack.pop();
    }
}
