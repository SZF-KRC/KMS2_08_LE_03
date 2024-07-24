package com.bildungsinstitut.view;

import com.bildungsinstitut.database.CourseData;
import com.bildungsinstitut.database.CourseWithStudentData;
import com.bildungsinstitut.database.EmployeeData;
import com.bildungsinstitut.management.InstitutionManagement;
import com.bildungsinstitut.model.Course;
import com.bildungsinstitut.model.CourseWithStudent;
import com.bildungsinstitut.model.Employee;
import com.bildungsinstitut.model.Student;
import com.bildungsinstitut.util.UniqueID;
import com.bildungsinstitut.util.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class CourseWindowController {
    @FXML
    private ComboBox<Student> comboBoxStudent;

    @FXML
    private ComboBox<Course> comboBoxCourse;

    @FXML
    private ComboBox<Employee> comboBoxTrainer;

    @FXML
    private TableView<Course> tableViewMakeCourse;

    @FXML
    private TableColumn<Course, String> colCourse;

    @FXML
    private TableColumn<Course, String> colTrainer;

    @FXML
    private TextField txtFullName;

    @FXML
    private Button btnAddCourse;

    @FXML
    private Button btnRemoveCourse;

    @FXML
    private Button btnUpdateCourse;

    @FXML
    private Button btnAddToCourse;

    @FXML
    private Button btnRemoveFromCourse;

    @FXML
    private TableView<CourseWithStudent> tableViewCourse;

    @FXML
    private TableColumn<CourseWithStudent, String> colCourseName;

    @FXML
    private TableColumn<CourseWithStudent,String> colCourseStudent;

    @FXML
    private TableColumn<CourseWithStudent, String> colCourseTrainer;

    private final ObservableList<Course> courses = FXCollections.observableArrayList();
    private final ObservableList<CourseWithStudent> courseWithStudents = FXCollections.observableArrayList();

    private final InstitutionManagement<Course> institutionManagementCourse = new InstitutionManagement<>();
    private final InstitutionManagement<CourseWithStudent> institutionManagementCourseWithStudent = new InstitutionManagement<>();

    private void loadData(){
        courses.addAll(CourseData.getAllCourses());
        courses.forEach(institutionManagementCourse::add);

        courseWithStudents.addAll(CourseWithStudentData.getAllDataFromCourses());
        courseWithStudents.forEach(institutionManagementCourseWithStudent::add);

        List<Employee> employees = EmployeeData.getAllEmployees();
        comboBoxTrainer.setItems(FXCollections.observableArrayList(employees));
    }



    @FXML
    public void initialize(){
        setupButtonHandlers(btnAddCourse);
        setupButtonHandlers(btnAddToCourse);
        setupButtonHandlers(btnRemoveCourse);
        setupButtonHandlers(btnUpdateCourse);
        setupButtonHandlers(btnRemoveFromCourse);

        loadData();
    }

    @FXML
    private void addCourse() {
        if (ValidationUtil.validateInputCourse(txtFullName)){
            String courseName = txtFullName.getText().trim();
            String trainerID = comboBoxTrainer.getValue().getId();
            Course course = new Course(Id(),courseName,trainerID);

            courses.add(course);
            CourseData.addCourse(course);
            institutionManagementCourse.add(course);

        }
    }

    @FXML
    private void removeCourse() {
    }

    @FXML
    private void updateCourse() {
    }

    @FXML
    private void addToCourse() {
    }

    @FXML
    private void removeFromCourse() {
    }



    private void setupButtonHandlers(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> button.setStyle("-fx-background-color: #c3d8ff; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> button.setStyle("-fx-background-color: #74BFcf; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> button.setStyle("-fx-background-color: #638ac8; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> button.setStyle("-fx-background-color: #c3d8ff; -fx-font-weight: bold; -fx-font-size: 12px;"));
    }



    private String Id(){
        return UniqueID.generateUniqueID();
    }
}
