package com.cal.calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.Stack;

import java.io.IOException;

public class Calculator extends Application {

    public static float evaluate(String expression) {

        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Float> values = new Stack<Float>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++) {

            // If Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // If Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9'|| tokens[i]=='.') {
                StringBuffer sbuf = new StringBuffer();

                // There may be more than one digit in number
                while (i < tokens.length && ('0' <= tokens[i] && tokens[i] <= '9' || tokens[i]=='.'))
                    sbuf.append(tokens[i++]);
                System.out.println("sbuf = " + sbuf);
                values.push(Float.parseFloat(sbuf.toString()));

                System.out.println("Pushed " + sbuf.toString() + " to values stack");
                // right now the i points to the character next to the digit,
                // since the for loop also increases the i, we would skip one
                // token position; we need to decrease the value of i by 1 to  correct the offset.
                i--;
            }


            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

            // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(),values.pop(),values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops' to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(),  values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
            System.out.println(values + "\n");
        }

        // Entire expression has been parsed at this point, apply remaining ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher or same precedence as 'op1', otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a' and 'b'. Return the result.
    public static float applyOp(char op, float b, float a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            case '%':
                return a % b;
        }
        return 0;
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Calculator.class.getResource("Calculator.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        System.out.println(evaluate("100 * ( 2 + 12 ) / 14"));
    }

}
