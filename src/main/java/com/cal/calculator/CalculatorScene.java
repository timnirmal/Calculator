package com.cal.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorScene {

    private String expression = "";
    boolean head = true; //True for expression, false for Trigonometric
    private String triExpression = "";
    private String triExShow = "";

    @FXML
    public Label welcomeText;

    @FXML
    public Label Trigonometric;

    // Write new Char to the right of cursor
    private void WriteLeft(String text) {
        if (expression.contains("|")) {
            System.out.println("Contains |");
            // find index of "|"
            int index = expression.indexOf("|");
            // get the expression before "|"
            String left = expression.substring(0, index);
            // get the expression after "|"
            String right = expression.substring(index + 1);

            expression = left + "|" + text + right;

            System.out.println("The expression is " + expression);
        }
        else {
            expression += text;
        }

        welcomeText.setText(expression);
    }

    // Numbers and Operators
    public void onButtonClick(ActionEvent actionEvent) {
        String text = ((Button) actionEvent.getSource()).getText();
        if (head) {
            // if expression contains "|"
            WriteLeft(text);

        }
        else {
            // remove "|" if contains
            if (expression.contains("|")) expression = expression.replace("|", "");

            triExpression += text;
            triExShow += text;
            Trigonometric.setText(triExShow);
        }
    }

    // Closing Brackets
    public void onCloseBracketButtonClick(ActionEvent actionEvent) {
        if (head) {
            String text = ((Button) actionEvent.getSource()).getText();
            welcomeText.setText(expression);

            // if expression contains "|"
            WriteLeft(text);

        }
        else {
            // Print solved, triExpression, and triExShow
            System.out.println("The Tri expression is " + triExpression);
            System.out.println("The Show expression is " + triExShow);


            String solved = Calculator.evaluateTri(triExpression);
            System.out.println("The solved expression is " + solved);

            // Covert -1S to Sin-1, -1C to Cos-1, -1T to Tan-1
            String solvedShow = solved;
            if (solved.contains("-1S")) {
                solvedShow = solved.replace("-1S", "Sin-1");
            }
            else if (solved.contains("-1C")) {
                solvedShow = solved.replace("-1C", "Cos-1");
            }
            else if (solved.contains("-1T")) {
                solvedShow = solved.replace("-1T", "Tan-1");
            }

            triExpression = solved;
            triExShow = solvedShow;

            if (triExpression.contains("Sin") || triExpression.contains("Cos") || triExpression.contains("Tan") || triExpression.contains("-1S") || triExpression.contains("-1C") || triExpression.contains("-1T")) {
                Trigonometric.setText(solvedShow);
                welcomeText.setText(solvedShow);
                head = false;
            }
            else {
                Trigonometric.setText(solved);
                triExpression = "";
                triExShow = "";
                expression += solved;
                welcomeText.setText(expression);
                Trigonometric.setText("");
                head = true;
            }
        }
    }

    // Solve the expression except Trigonometric
    public void onEvalButtonClick(ActionEvent actionEvent) {
        // remove "|" if contains
        if (expression.contains("|")) expression = expression.replace("|", "");

        String answer = String.valueOf(Calculator.evaluate(expression));
        welcomeText.setText(answer);
        expression = answer;
    }

    // Clear the expression
    public void onClearButtonClick(ActionEvent actionEvent) {
        expression = "";
        welcomeText.setText("");
        triExpression = "";
        triExShow = "";
        Trigonometric.setText("");
    }

    // Clear One character
    public void onAClearButtonClick(ActionEvent actionEvent) {
        // Clear last character
        if (expression.length() > 0) {
            expression = expression.substring(0, expression.length() - 1);
            welcomeText.setText(expression);
        }
    }

    // Trigonometric functions Solve
    public void onTriButtonClick(ActionEvent actionEvent) {
        // remove "|" if contains
        if (expression.contains("|")) expression = expression.replace("|", "");

        String text = ((Button) actionEvent.getSource()).getText();
        triExpression += text;
        triExpression += "(";
        triExShow += text+"(";
        Trigonometric.setText(triExShow);
        head = false;
    }

    // Inverse Trigonometric functions Solve
    public void onTriInvButtonClick(ActionEvent actionEvent) {
        // remove "|" if contains
        if (expression.contains("|")) expression = expression.replace("|", "");

        String text;
        switch (((Button) actionEvent.getSource()).getText()) {
            case "Sin-1":
                text = "-1S";
                break;
            case "Cos-1":
                text = "-1C";
                break;
            case "Tan-1":
                text = "-1T";
                break;
            default:
                text = ((Button) actionEvent.getSource()).getText();
                break;
        }
        System.out.println("The text is " + text);

        triExpression += text;
        triExpression += "(";
        triExShow += ((Button) actionEvent.getSource()).getText()+"(";
        Trigonometric.setText(triExShow);
        head = false;
    }

    // Move cursor to the left
    public void onLeftButtonClick(ActionEvent actionEvent) {
        // if head is true
        //if (head) {
            // if expression doesnt have "|"
            if (!expression.contains("|")) {
                // Add "|" to before last character of expression
                expression = expression.substring(0, expression.length() - 1) + "|" + expression.substring(expression.length()-1);
                welcomeText.setText(expression);
            }
            else {
                // get the index of "|"
                int index = expression.indexOf("|");
                // remove the "|"
                expression = expression.substring(0, index) + expression.substring(index + 1);

                // if index is not 0
                if (index != 1) {
                    // move "|" to number before current position
                    expression = expression.substring(0, index - 1) + "|" + expression.substring(index - 1);
                    welcomeText.setText(expression);
                }
                else {
                    System.out.println("The index is " + index);
                    welcomeText.setText(expression);
                }
            }
    }

    // Move cursor to the right
    public void onRightButtonClick(ActionEvent actionEvent) {
        // if expression doesnt have "|"
        if (!expression.contains("|")) {
            // Add "|" to before last character of expression
            expression = expression.charAt(0) + "|" + expression.substring(1);
            welcomeText.setText(expression);
        }
        else {
            // get the index of "|"
            int index = expression.indexOf("|");
            // remove the "|"
            expression = expression.substring(0, index) + expression.substring(index + 1);

            // if index is not last character
            if (index != expression.length() - 1) {
                // move "|" to number after current position
                expression = expression.substring(0, index + 1) + "|" + expression.substring(index + 1);
                welcomeText.setText(expression);
            }
            else {
                System.out.println("The index is " + index);
                welcomeText.setText(expression);
                return;
            }
        }
    }
}