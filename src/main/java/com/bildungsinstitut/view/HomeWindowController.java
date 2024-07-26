package com.bildungsinstitut.view;

import com.bildungsinstitut.database.CourseData;
import com.bildungsinstitut.database.CourseWithStudentData;
import com.bildungsinstitut.database.EmployeeData;
import com.bildungsinstitut.database.StudentData;
import com.bildungsinstitut.model.Course;
import com.bildungsinstitut.model.CourseWithStudent;
import com.bildungsinstitut.model.Employee;
import com.bildungsinstitut.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.stream.Collectors;

public class HomeWindowController {

    @FXML
    private TableColumn<CourseWithStudent, String> colStudent;

    @FXML
    private TableColumn<CourseWithStudent, String> colStudentEmail;

    @FXML
    private TableColumn<CourseWithStudent, String> colTrainer;

    @FXML
    private TableColumn<CourseWithStudent, String> colTrainerEmail;

    @FXML
    private ComboBox<String> comboBoxCourses;

    @FXML
    private TableView<CourseWithStudent> tableView;

    private final ObservableList<CourseWithStudent> courseWithStudentsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Loading courses with students into ComboBox
        List<CourseWithStudent> allCourseWithStudents = CourseWithStudentData.getAllDataFromCourses();
        List<Course> courses = CourseData.getAllCourses();

        // We filter unique course names and sort them alphabetically
        List<String> uniqueCourseNames = courses.stream()
                .filter(course -> allCourseWithStudents.stream()
                        .anyMatch(cws -> cws.getCourseID().equals(course.getId())))
                .map(Course::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        comboBoxCourses.setItems(FXCollections.observableArrayList(uniqueCourseNames));

        // Predefined first display in ComboBox
        if (!uniqueCourseNames.isEmpty()) {
            comboBoxCourses.getSelectionModel().select(0);
            loadCourseDetails(uniqueCourseNames.get(0));
        }

        // TableView column settings
        colStudent.setCellValueFactory(cellData -> {
            CourseWithStudent courseWithStudent = cellData.getValue();
            Student student = StudentData.getAllStudents().stream()
                    .filter(s -> s.getId().equals(courseWithStudent.getStudentID()))
                    .findFirst()
                    .orElse(null);
            return new SimpleStringProperty(student != null ? student.getName() : "Unknown");
        });

        colStudentEmail.setCellValueFactory(cellData -> {
            CourseWithStudent courseWithStudent = cellData.getValue();
            Student student = StudentData.getAllStudents().stream()
                    .filter(s -> s.getId().equals(courseWithStudent.getStudentID()))
                    .findFirst()
                    .orElse(null);
            return new SimpleStringProperty(student != null ? student.getEmail() : "Unknown");
        });

        colTrainer.setCellValueFactory(cellData -> {
            CourseWithStudent courseWithStudent = cellData.getValue();
            Course course = courses.stream()
                    .filter(c -> c.getId().equals(courseWithStudent.getCourseID()))
                    .findFirst()
                    .orElse(null);
            Employee trainer = course != null ? EmployeeData.getAllEmployees().stream()
                    .filter(e -> e.getId().equals(course.getTrainerID()))
                    .findFirst()
                    .orElse(null) : null;
            return new SimpleStringProperty(trainer != null ? trainer.getName() : "Unknown");
        });

        colTrainerEmail.setCellValueFactory(cellData -> {
            CourseWithStudent courseWithStudent = cellData.getValue();
            Course course = courses.stream()
                    .filter(c -> c.getId().equals(courseWithStudent.getCourseID()))
                    .findFirst()
                    .orElse(null);
            Employee trainer = course != null ? EmployeeData.getAllEmployees().stream()
                    .filter(e -> e.getId().equals(course.getTrainerID()))
                    .findFirst()
                    .orElse(null) : null;
            return new SimpleStringProperty(trainer != null ? trainer.getEmail() : "Unknown");
        });

        comboBoxCourses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadCourseDetails(newValue);
            }
        });

        tableView.setRowFactory(tv -> new TableRow<CourseWithStudent>() {
            @Override
            protected void updateItem(CourseWithStudent item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("-fx-background-color: #E0F7FA;");  // Set the background color for empty rows
                } else {
                    if (isSelected()) {
                        setStyle("-fx-background-color: #3b6770;");  // Set the background color for selected row
                    } else {
                        setStyle("-fx-background-color: " + (getIndex() % 2 == 0 ? "#f0f8ff" : "#e6e6fa") + ";");
                    }
                }
            }
        });

        // Setting the background for the TableView when it is empty
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #E0F7FA;");
        tableView.setPlaceholder(stackPane);
    }

    private void loadCourseDetails(String courseName) {
        List<CourseWithStudent> courseWithStudentList = CourseWithStudentData.getAllDataFromCourses().stream()
                .filter(cws -> {
                    Course course = CourseData.getAllCourses().stream()
                            .filter(c -> c.getId().equals(cws.getCourseID()))
                            .findFirst()
                            .orElse(null);
                    return course != null && course.getName().equals(courseName);
                })
                .collect(Collectors.toList());
        courseWithStudentsList.setAll(courseWithStudentList);
        tableView.setItems(courseWithStudentsList);
    }
}
