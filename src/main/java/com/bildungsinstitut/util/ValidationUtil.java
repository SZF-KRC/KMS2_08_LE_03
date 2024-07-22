package com.bildungsinstitut.util;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidAge(String age) {
        if (age == null || !age.matches("\\d+")) {
            return false;
        }
        int ageInt = Integer.parseInt(age);
        return ageInt >= 18 && ageInt <= 120;
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isAlpha(String value) {
        return value != null && value.matches("[a-zA-Z\\s]+");
    }

    public static boolean validateInputsEmployee(TextField txtFullName,TextField txtEmail, TextField txtAge, TextField txtPosition){
        if (!ValidationUtil.isNotEmpty(txtFullName.getText()) || !ValidationUtil.isAlpha(txtFullName.getText())){
            showAlert("Invalid Full Name", "Full Name must contain only letters and cannot be empty.");
            return false;
        }

        if (!ValidationUtil.isNotEmpty(txtEmail.getText()) || !ValidationUtil.isValidEmail(txtEmail.getText())){
            showAlert("Invalid Email", "Email is not in the correct format or is empty.");
            return false;
        }
        if (!ValidationUtil.isNotEmpty(txtAge.getText()) || !ValidationUtil.isValidAge(txtAge.getText())) {
            showAlert("Invalid Age", "Age must be a number between 18 and 120 and cannot be empty.");
            return false;
        }
        if (!ValidationUtil.isNotEmpty(txtPosition.getText()) || !ValidationUtil.isAlpha(txtPosition.getText())) {
            showAlert("Invalid Position", "Position must contain only letters and cannot be empty.");
            return false;
        }

        return true;
    }

    public static boolean validateInputsStudent(TextField txtFullName,TextField txtEmail, TextField txtAge){
        if (!ValidationUtil.isNotEmpty(txtFullName.getText()) || !ValidationUtil.isAlpha(txtFullName.getText())){
            showAlert("Invalid Full Name", "Full Name must contain only letters and cannot be empty.");
            return false;
        }

        if (!ValidationUtil.isNotEmpty(txtEmail.getText()) || !ValidationUtil.isValidEmail(txtEmail.getText())){
            showAlert("Invalid Email", "Email is not in the correct format or is empty.");
            return false;
        }
        if (!ValidationUtil.isNotEmpty(txtAge.getText()) || !ValidationUtil.isValidAge(txtAge.getText())) {
            showAlert("Invalid Age", "Age must be a number between 18 and 120 and cannot be empty.");
            return false;
        }

        return true;
    }

    private static void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
