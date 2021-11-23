package com.cal.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

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
            String substring = triExpression.substring(firstOpenBracket + 1);

            float ans = 0;
            double pi = Math.PI;

            String substringToDegree = degree ? substring : String.valueOf(Math.toDegrees(Double.parseDouble(substring)) * 180 / pi);

            switch (triExpression.substring(firstOpenBracket - 3, firstOpenBracket)) {
                case "Sin" -> ans = degree ? (float) Math.sin(evaluate(substring) * pi / 180) : (float) Math.sin(evaluate(substring));
                case "Cos" -> ans = degree ? (float) Math.cos(evaluate(substring) * pi / 180) : (float) Math.cos(evaluate(substring));
                case "Tan" -> ans = degree ? (float) Math.tan(evaluate(substring) * pi / 180) : (float) Math.tan(evaluate(substring));
                case "-1S" -> {
                    ans = (float) Math.asin(evaluate(substringToDegree)); // output in radians
                    if (degree) ans = (float) (ans * 180 / pi); // output in degrees
                }
                case "-1C" -> {
                    ans = (float) Math.acos(evaluate(substringToDegree));
                    if (degree) ans = (float) (ans * 180 / pi);
                }
                case "-1T" -> {
                    ans = (float) Math.atan(evaluate(substringToDegree));
                    if (degree) ans = (float) (ans * 180 / pi);
                }
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
        // Get absolute path of the current directory
        String filePath = new File("").getAbsolutePath();
        // Set the icon of the calculator
        stage.getIcons().add(new Image("file:\\"+filePath+"\\src\\main\\java\\com\\cal\\calculator\\icons8-calculator-64.jpg"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
