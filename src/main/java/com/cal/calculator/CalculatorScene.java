package com.cal.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorScene {

    private String expression = "";


    @FXML
    public Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onButtonClick(ActionEvent actionEvent) {
        String text = ((Button) actionEvent.getSource()).getText();
        expression += text;
        welcomeText.setText(expression);
    }

    public void onEvalButtonClick(ActionEvent actionEvent) {
        welcomeText.setText(expression + " = " + Calculator.evaluate(expression));
    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        expression = "";
        welcomeText.setText("");
    }
}