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
    private boolean degree = true;

    @FXML
    public Label MainExpression;

    @FXML
    public Label Trigonometric;

    // Write new Char to the right of cursor
    private void WriteLeft(String text) {
        if (expression.contains("|")) {
            // find index of "|"
            int index = expression.indexOf("|");
            // get the expression before "|"
            String left = expression.substring(0, index);
            // get the expression after "|"
            String right = expression.substring(index + 1);

            expression = left + "|" + text + right;
        }
        else {
            expression += text;
        }

        MainExpression.setText(expression);
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
            MainExpression.setText(expression);

            // if expression contains "|"
            WriteLeft(text);
        }
        else {
            String solved = Calculator.evaluateTri(triExpression, degree);

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
                MainExpression.setText(solvedShow);
                head = false;
            }
            else {
                Trigonometric.setText(solved);
                triExpression = "";
                triExShow = "";
                expression += solved;
                MainExpression.setText(expression);
                Trigonometric.setText("");
                head = true;
            }
        }
    }

    // Solve the expression except Trigonometric
    public void onEvalButtonClick() {
        // remove "|" if contains
        if (expression.contains("|")) expression = expression.replace("|", "");

        String answer = String.valueOf(Calculator.evaluate(expression));
        MainExpression.setText(answer);
        expression = answer;
    }

    // Clear the expression
    public void onClearButtonClick() {
        expression = "";
        MainExpression.setText("");
        triExpression = "";
        triExShow = "";
        Trigonometric.setText("");
    }

    // Clear One character
    public void onAClearButtonClick() {
        // Clear last character
        if (expression.length() > 0) {
            expression = expression.substring(0, expression.length() - 1);
            MainExpression.setText(expression);
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

        String text = switch (((Button) actionEvent.getSource()).getText()) {
            case "Sin-1" -> "-1S";
            case "Cos-1" -> "-1C";
            case "Tan-1" -> "-1T";
            default -> ((Button) actionEvent.getSource()).getText();
        };

        triExpression += text;
        triExpression += "(";
        triExShow += ((Button) actionEvent.getSource()).getText()+"(";
        Trigonometric.setText(triExShow);
        head = false;
    }

    // Move cursor to the left
    public void onLeftButtonClick() {
        // if head is true
        //if (head) {
            // if expression doesnt have "|"
            if (!expression.contains("|")) {
                // Add "|" to before last character of expression
                expression = expression.substring(0, expression.length() - 1) + "|" + expression.substring(expression.length()-1);
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
                }
            }
        MainExpression.setText(expression);
    }

    // Move cursor to the right
    public void onRightButtonClick() {
        // if expression doesnt have "|"
        if (!expression.contains("|")) {
            // Add "|" to before last character of expression
            expression = expression.charAt(0) + "|" + expression.substring(1);
            MainExpression.setText(expression);
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
                MainExpression.setText(expression);
            }
            else {
                MainExpression.setText(expression);
                return;
            }
        }
    }

    public void onDRButtonClick(ActionEvent actionEvent) {
        degree = !degree;
        // if button text is "Degree"
        if (((Button) actionEvent.getSource()).getText().equals("D")) {
            // change button text to "Radian"
            ((Button) actionEvent.getSource()).setText("R");
        }
        else {
            // change button text to "Degree"
            ((Button) actionEvent.getSource()).setText("D");
        }
    }

}