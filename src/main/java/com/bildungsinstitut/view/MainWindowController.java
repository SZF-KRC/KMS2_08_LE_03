package com.bildungsinstitut.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private Button btnEmployees;

    @FXML
    private Button btnCourses;

    @FXML
    private Button btnStudents;

    @FXML
    private StackPane contentPane;

    @FXML
    public void btnEmployeesAction() {
        loadWindow("/com.bildungsinstitut/EmployeeWindow.fxml");
    }

    @FXML
    public void btnStudentsAction() {
        loadWindow("/com.bildungsinstitut/StudentWindow.fxml");
    }

    @FXML
    public void btnCoursesAction() {
        loadWindow("/com.bildungsinstitut/CourseWindow.fxml");
    }

    private void loadWindow(String fxmlFile) {
        try {
            Node newContent = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentPane.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
