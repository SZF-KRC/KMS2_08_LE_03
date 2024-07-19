package com.example.bildungsinstitut;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private  Label pressButtonLabel;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void onPressButtonClick() {pressButtonLabel.setText("Tak predsa to pojde!");}
}