package com.example.lab6;

import java.util.Stack;
import java.util.StringTokenizer;

class EquationParser {

    EquationParser() {}

    double convert(String equation) {

        // Remove any potential whitespace within given equation.
        equation = equation.replaceAll("[\t\n ]", "");

        // Initializes StringTokenizer for parsing infix string equation.
        String decimeter = "+-*/%=";
        StringTokenizer equationTokens = new StringTokenizer(equation, decimeter,true);
        // Stacks that'll contain appropriate tokens.
        Stack<String> operators = new Stack<>();
        Stack<String> values = new Stack<>();

        String currentToken, lastToken;

        // Loops through tokens within StringTokenizer.
        while (equationTokens.hasMoreTokens()) {
            // Grabs the current token within the StringTokenizer.
            currentToken = equationTokens.nextToken();
            // Pushes token to current stack based on their symbol.
            if (currentToken.equals("+") || currentToken.equals("-") ||
                    currentToken.equals("*") || currentToken.equals("/") ||
                    currentToken.equals("%") || currentToken.equals("=")) {
                operators.push(currentToken);
            } else values.push(currentToken);

            orderOfOperationsHandler(values, operators);

        }

        lastToken = values.pop();
        return Double.parseDouble(lastToken);
    }

    private int parseOrderOfOperations(String operator) {
        switch (operator) {
            case "*":
            case "/":
            case "%":
                return 1;
            case "+":
            case "-":
                return 2;
            case "=":
                return 3;
            default:
                return Integer.MIN_VALUE;
        }
    }

    private void orderOfOperationsHandler(Stack<String> values, Stack<String> operators) {

        String firstOperator, secondOperator, firstValue, secondValue;

        while (operators.size() >= 2) {

            firstOperator = operators.pop();
            secondOperator = operators.pop();

            if (parseOrderOfOperations(firstOperator) < parseOrderOfOperations(secondOperator)) {
                operators.push(secondOperator);
                operators.push(firstOperator);
                return;
            } else {
                firstValue = values.pop();
                secondValue = values.pop();
                values.push(calculate(secondValue, firstValue, secondOperator));
                operators.push(firstOperator);
            }
        }

    }

    private String calculate(String firstValue, String secondValue, String operator) {

        double result = 0.0;

        double fValue = Double.parseDouble(firstValue);
        double sValue = Double.parseDouble(secondValue);

        switch (operator) {
            case "+": result =  fValue + sValue; break;
            case "-": result = fValue - sValue; break;
            case "*": result = fValue * sValue; break;
            case "%": try {
                result = fValue % sValue;
                if (Double.isNaN(result)) {
                    throw new ArithmeticException();
                }
                else break;
            } catch(ArithmeticException e) {
                System.out.print("Error: Cannot mod by 0!");
                break;
            }
            case "/": try {
                result = fValue / sValue;
                if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY) {
                    throw new ArithmeticException();
                }
                else break;
            } catch(ArithmeticException e) {
                System.out.print("Error: Cannot divide by 0!");
                break;
            }
        }
        return String.valueOf(result);
    }
}