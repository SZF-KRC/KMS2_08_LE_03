package com.bildungsinstitut.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class MainWindowController {
    @FXML
    private Button btnHome;

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
        loadWindow("/com/bildungsinstitut/bildungsinstitut/EmployeeWindow.fxml");
    }

    @FXML
    public void btnStudentsAction() {
        loadWindow("/com/bildungsinstitut/bildungsinstitut/StudentWindow.fxml");
    }

    @FXML
    public void btnCoursesAction() {
        loadWindow("/com/bildungsinstitut/bildungsinstitut/CourseWindow.fxml");
    }

    @FXML
    public void btnHomeAction() {
        loadWindow("/com/bildungsinstitut/bildungsinstitut/HomeWindow.fxml");
    }

    private void loadWindow(String fxmlFile) {
        try {
            Node newContent = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentPane.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        setupButtonHandlers(btnStudents);
        setupButtonHandlers(btnEmployees);
        setupButtonHandlers(btnCourses);
        setupButtonHandlers(btnHome);
        btnHomeAction();
    }

    private void setupButtonHandlers(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> button.setStyle("-fx-background-color: #c3d8ff; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> button.setStyle("-fx-background-color: #74BFcf; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> button.setStyle("-fx-background-color: #638ac8; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> button.setStyle("-fx-background-color: #c3d8ff; -fx-font-weight: bold; -fx-font-size: 12px;"));
    }


}
