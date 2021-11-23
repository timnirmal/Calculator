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

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onButtonClick(ActionEvent actionEvent) {
        String text = ((Button) actionEvent.getSource()).getText();
        if (head) {
            expression += text;
            welcomeText.setText(expression);
        }
        else {
            triExpression += text;
            triExShow += text;
            Trigonometric.setText(triExShow);
        }
    }

    public void onCloseBracketButtonClick(ActionEvent actionEvent) {
        if (head) {
            String text = ((Button) actionEvent.getSource()).getText();
            expression += text;
            welcomeText.setText(expression);
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
                head = false;
            }
            else {
                Trigonometric.setText(triExShow + ") = " + solved);
                // TODO : Add the result to expression | Clear triExpression
                triExpression = "";
                expression += solved;
                welcomeText.setText(expression);
                //Trigonometric.setText("");
                head = true;
            }
        }
    }

    public void onEvalButtonClick(ActionEvent actionEvent) {
        welcomeText.setText(expression + " = " + Calculator.evaluate(expression));
    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        expression = "";
        welcomeText.setText("");
        triExpression = "";
        triExShow = "";
        Trigonometric.setText("");
    }

    public void onTriButtonClick(ActionEvent actionEvent) {
        String text = ((Button) actionEvent.getSource()).getText();
        triExpression += text;
        triExpression += "(";
        triExShow += text+"(";
        Trigonometric.setText(triExShow);
        head = false;
    }


    public void onTriInvButtonClick(ActionEvent actionEvent) {
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
}