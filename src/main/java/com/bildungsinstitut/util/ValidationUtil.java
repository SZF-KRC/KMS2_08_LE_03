package com.bildungsinstitut.util;

import com.bildungsinstitut.model.Course;
import com.bildungsinstitut.model.Student;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static boolean isValidAge(String age) {
        if (age == null || !age.matches("\\d+")) {
            return false;
        }
        int ageInt = Integer.parseInt(age);
        return ageInt >= 18 && ageInt <= 120;
    }

    private static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean isAlpha(String value) {
        return value != null && value.matches("[a-zA-Z\\s]+");
    }

    public static boolean validateInputCourse(TextField txtFullName){
        if (!isNotEmpty(txtFullName.getText()) || !isAlpha(txtFullName.getText())){
            showAlert("Invalid Course Name", "Course Name must contain only letters and cannot be empty.");
            return false;
        }
        return true;
    }

    public static boolean validateInputsEmployee(TextField txtFullName,TextField txtEmail, TextField txtAge, TextField txtPosition){
        if (!isNotEmpty(txtFullName.getText()) || !isAlpha(txtFullName.getText())){
            showAlert("Invalid Full Name", "Full Name must contain only letters and cannot be empty.");
            return false;
        }

        if (!isNotEmpty(txtEmail.getText()) || !isValidEmail(txtEmail.getText())){
            showAlert("Invalid Email", "Email is not in the correct format or is empty.");
            return false;
        }
        if (!isNotEmpty(txtAge.getText()) || !isValidAge(txtAge.getText())) {
            showAlert("Invalid Age", "Age must be a number between 18 and 120 and cannot be empty.");
            return false;
        }
        if (!isNotEmpty(txtPosition.getText()) || !isAlpha(txtPosition.getText())) {
            showAlert("Invalid Position", "Position must contain only letters and cannot be empty.");
            return false;
        }

        return true;
    }

    public static boolean validateInputsStudent(TextField txtFullName,TextField txtEmail, TextField txtAge){
        if (!isNotEmpty(txtFullName.getText()) || !isAlpha(txtFullName.getText())){
            showAlert("Invalid Full Name", "Full Name must contain only letters and cannot be empty.");
            return false;
        }

        if (!isNotEmpty(txtEmail.getText()) || !isValidEmail(txtEmail.getText())){
            showAlert("Invalid Email", "Email is not in the correct format or is empty.");
            return false;
        }
        if (!isNotEmpty(txtAge.getText()) || !isValidAge(txtAge.getText())) {
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
