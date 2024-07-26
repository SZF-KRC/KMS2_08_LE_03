package com.bildungsinstitut.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.Objects;

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
    private StackPane imagePane;

    private ImageView imageView;
    private Image homeImage;
    private Image studentsImage;
    private Image employeesImage;
    private Image coursesImage;

    @FXML
    private void btnEmployeesAction() {
        loadWindow("/com/bildungsinstitut/bildungsinstitut/EmployeeWindow.fxml");
        imageView.setImage(employeesImage);
    }

    @FXML
    private void btnStudentsAction() {
        loadWindow("/com/bildungsinstitut/bildungsinstitut/StudentWindow.fxml");
        imageView.setImage(studentsImage);
    }

    @FXML
    private void btnCoursesAction() {
        loadWindow("/com/bildungsinstitut/bildungsinstitut/CourseWindow.fxml");
        imageView.setImage(coursesImage);
    }

    @FXML
    private void btnHomeAction() {
        loadWindow("/com/bildungsinstitut/bildungsinstitut/HomeWindow.fxml");
        imageView.setImage(homeImage);
    }

    private void loadWindow(String fxmlFile) {
        try {
            Node newContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
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

        // load images
        homeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/bildungsinstitut/bildungsinstitut/pictures/home.png")));
        studentsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/bildungsinstitut/bildungsinstitut/pictures/students.png")));
        employeesImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/bildungsinstitut/bildungsinstitut/pictures/employees.png")));
        coursesImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/bildungsinstitut/bildungsinstitut/pictures/courses.png")));

        // Initialize the ImageView and add it to the imagePane
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(imagePane.getPrefWidth());
        imageView.setFitHeight(imagePane.getPrefHeight());
        imagePane.getChildren().add(imageView);

        btnHomeAction();
    }

    private void setupButtonHandlers(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> button.setStyle("-fx-background-color: #c3d8ff; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> button.setStyle("-fx-background-color: #74BFcf; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> button.setStyle("-fx-background-color: #638ac8; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> button.setStyle("-fx-background-color: #c3d8ff; -fx-font-weight: bold; -fx-font-size: 12px;"));
    }
}