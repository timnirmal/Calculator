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

        // Stack for values
        Stack<Float> values = new Stack<Float>();

        // Stack for operators
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++) {

            // If Current token is a ' ', skip it
            if (tokens[i] == ' ')
                continue;

            // If Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9'|| tokens[i]=='.') {
                StringBuffer sbuf = new StringBuffer();

                // There may be more than one digit in number
                while (i < tokens.length && ('0' <= tokens[i] && tokens[i] <= '9' || tokens[i]=='.'))
                    sbuf.append(tokens[i++]);
                values.push(Float.parseFloat(sbuf.toString()));

                i--;
            }

            // If Current token is an opening brace, push it to ops
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

            // If Closing brace encountered, solve entire brace
            else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(),values.pop(),values.pop()));
                ops.pop();
            }

            // If Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '%') {
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        return values.pop();
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

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

    public static String evaluateTri(String triExpression, boolean degree) {
        // Find first ( from the end of the string
        int firstOpenBracket = triExpression.lastIndexOf('(');

        // if string before that index is Sin
        if (firstOpenBracket > 0) {
            String substring = triExpression.substring(firstOpenBracket + 1, triExpression.length());

            float ans = 0;
            double pi = Math.PI;

            String substringToRadians = String.valueOf(Float.parseFloat(substring) * pi / 180);
            switch (triExpression.substring(firstOpenBracket - 3, firstOpenBracket)) {
                case "Sin":
                    ans = degree ? (float) Math.sin(evaluate(substring) * pi / 180) : (float) Math.sin(evaluate(substring));
                    break;
                case "Cos":
                    ans = degree ? (float) Math.cos(evaluate(substring) * pi / 180) : (float) Math.cos(evaluate(substring));
                    //ans = (float) Math.cos(evaluate(substring) ); // if you want to use radians
                    //ans = (float) Math.cos(evaluate(substring) * pi / 180); // if you want to use degrees
                    break;
                case "Tan":
                    ans = degree ? (float) Math.tan(evaluate(substring) * pi / 180) : (float) Math.tan(evaluate(substring));
                    break;
                case "-1S":
                    if (degree) {
                        // substring to radians
                        ans = (float) Math.asin(evaluate(substringToRadians) * pi / 180);
                    }
                    else{
                        ans = (float) Math.asin(evaluate(substring));
                    }
                    //ans = (float) Math.asin(evaluate(substring)); // output in radians

                    break;
                case "-1C":
                    if (degree) {
                        // substring to radians
                        ans = (float) Math.acos(evaluate(substring));
                    }
                    else{
                        ans = (float) Math.acos(evaluate(substring));
                    }

                    break;
                case "-1T":
                    if (degree) {
                        // substring to radians
                        ans = (float) Math.atan(evaluate(substring));
                    }
                    else{
                        ans = (float) Math.atan(evaluate(substring));
                    }

                    break;
            }

            // Remove sin , cos, tan, -1S, -1C, -1T from the string
            triExpression = triExpression.substring(0, firstOpenBracket - 3) + ans;


            //return String.valueOf(ans);
            return triExpression;
        }




        return String.valueOf(evaluate(triExpression));
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
    }

}
