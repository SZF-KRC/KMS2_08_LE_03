package com.bildungsinstitut.view;

import com.bildungsinstitut.database.CourseData;
import com.bildungsinstitut.database.CourseWithStudentData;
import com.bildungsinstitut.database.EmployeeData;
import com.bildungsinstitut.database.StudentData;
import com.bildungsinstitut.management.InstitutionManagement;
import com.bildungsinstitut.model.Course;
import com.bildungsinstitut.model.CourseWithStudent;
import com.bildungsinstitut.model.Employee;
import com.bildungsinstitut.model.Student;
import com.bildungsinstitut.util.UniqueID;
import com.bildungsinstitut.util.ValidationUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

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
    private List<Employee> employees = FXCollections.observableArrayList();

    private final InstitutionManagement<Course> institutionManagementCourse = new InstitutionManagement<>();
    private final InstitutionManagement<CourseWithStudent> institutionManagementCourseWithStudent = new InstitutionManagement<>();

    @FXML
    public void initialize(){
        setupButtonHandlers(btnAddCourse);
        setupButtonHandlers(btnAddToCourse);
        setupButtonHandlers(btnRemoveCourse);
        setupButtonHandlers(btnUpdateCourse);
        setupButtonHandlers(btnRemoveFromCourse);

        colCourse.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTrainer.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();
            String trainerName = course.getTrainerName(EmployeeData.getAllEmployees());
            return new SimpleStringProperty(trainerName);
        });
        tableViewMakeCourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtFullName.setText(newValue.getName());
                comboBoxTrainer.getSelectionModel().select(employees.stream()
                        .filter(employee -> employee.getId().equals(newValue.getTrainerID()))
                        .findFirst()
                        .orElse(null));
            }
        });

        colCourseName.setCellValueFactory(cellData -> {
            CourseWithStudent courseWithStudent = cellData.getValue();
            Course course = courses.stream()
                    .filter(c -> c.getId().equals(courseWithStudent.getCourseID()))
                    .findFirst()
                    .orElse(null);
            return new SimpleStringProperty(course != null ? course.getName() : "Unknown");
        });

        colCourseStudent.setCellValueFactory(cellData -> {
            CourseWithStudent courseWithStudent = cellData.getValue();
            Student student = comboBoxStudent.getItems().stream()
                    .filter(s -> s.getId().equals(courseWithStudent.getStudentID()))
                    .findFirst()
                    .orElse(null);
            return new SimpleStringProperty(student != null ? student.getName() : "Unknown");
        });

        colCourseTrainer.setCellValueFactory(cellData -> {
            CourseWithStudent courseWithStudent = cellData.getValue();
            Course course = courses.stream()
                    .filter(c -> c.getId().equals(courseWithStudent.getCourseID()))
                    .findFirst()
                    .orElse(null);
            if (course != null) {
                String trainerName = course.getTrainerName(employees);
                return new SimpleStringProperty(trainerName);
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });

        tableViewCourse.setRowFactory(tv -> new TableRow<CourseWithStudent>() {
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


        tableViewMakeCourse.setRowFactory(tv -> new TableRow<Course>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
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

        // Nastavenie pozadia pre TableView keď je prázdna
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #E0F7FA;");
        tableViewCourse.setPlaceholder(stackPane);
        tableViewMakeCourse.setPlaceholder(stackPane);

        loadData();
    }

    @FXML
    private void addCourse() {
        if (ValidationUtil.validateInputCourse(txtFullName)){
            String courseName = txtFullName.getText().trim();
            Employee selectedTrainer = comboBoxTrainer.getSelectionModel().getSelectedItem();

            if (selectedTrainer != null){
                String trainerID = selectedTrainer.getId();
                Course course = new Course(Id(),courseName,trainerID);

                if (institutionManagementCourse.existsCourse(course)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Duplicate Course");
                    alert.setHeaderText("Course Already Exists");
                    alert.setContentText("An course with the same name already exists. Do you want to add this course anyway?");

                    if (alert.showAndWait().get() != ButtonType.OK) {
                        return;
                    }
                }

                courses.add(course);
                CourseData.addCourse(course);
                institutionManagementCourse.add(course);
                tableViewMakeCourse.refresh();
                clearInput();
                loadData();
            }
        }
    }

    @FXML
    private void removeCourse() {
        Course selectedCourse = tableViewMakeCourse.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            // We check if the course has enrolled studentsv
            boolean hasStudents = courseWithStudents.stream()
                    .anyMatch(cws -> cws.getCourseID().equals(selectedCourse.getId()));

            if (hasStudents) {
                // Show notification with confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText("Course is Active");
                alert.setContentText("This course has enrolled students. Are you sure you want to delete this course and all its connections?");

                if (alert.showAndWait().get() == ButtonType.OK) {
                    // Remove all links to the course
                    List<CourseWithStudent> connectionsToRemove = courseWithStudents.stream()
                            .filter(cws -> cws.getCourseID().equals(selectedCourse.getId()))
                            .toList();
                    connectionsToRemove.forEach(cws -> {
                        courseWithStudents.remove(cws);
                        institutionManagementCourseWithStudent.remove(cws.getCourseID());
                        CourseWithStudentData.removeActiveCourse(cws);
                    });

                    // remove course
                    courses.remove(selectedCourse);
                    institutionManagementCourse.remove(selectedCourse.getId());
                    CourseData.deleteCourse(selectedCourse);
                    tableViewMakeCourse.refresh();
                }
            } else {
                // Delete course without notice
                courses.remove(selectedCourse);
                institutionManagementCourse.remove(selectedCourse.getId());
                CourseData.deleteCourse(selectedCourse);
                tableViewMakeCourse.refresh();
            }
        }
    }

    @FXML
    private void updateCourse() {
        Course selectedCourse = tableViewMakeCourse.getSelectionModel().getSelectedItem();
        if (selectedCourse !=null && ValidationUtil.validateInputCourse(txtFullName)){
            selectedCourse.setName(txtFullName.getText().trim());
            selectedCourse.setTrainerID(comboBoxTrainer.getSelectionModel().getSelectedItem().getId());

            tableViewMakeCourse.refresh();
            institutionManagementCourse.update(selectedCourse);
            CourseData.updateCourse(selectedCourse);

            clearInput();
        }
    }

    @FXML
    private void addToCourse() {
        Student selectedStudent = comboBoxStudent.getSelectionModel().getSelectedItem();
        Course selectedCourse = comboBoxCourse.getSelectionModel().getSelectedItem();

        if (selectedStudent != null && selectedCourse != null) {
            //Checks whether the record already exists
            boolean exists = courseWithStudents.stream()
                    .anyMatch(cws -> cws.getCourseID().equals(selectedCourse.getId()) && cws.getStudentID().equals(selectedStudent.getId()));

            if (!exists) {
                CourseWithStudent courseWithStudent = new CourseWithStudent(selectedCourse.getId(), selectedStudent.getId());
                courseWithStudents.add(courseWithStudent);
                CourseWithStudentData.addStudentToCourse(selectedStudent, selectedCourse);
                institutionManagementCourseWithStudent.add(courseWithStudent);
                tableViewCourse.refresh();
            } else {
                // Displays a warning if the record already exists
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Duplicate Entry");
                alert.setHeaderText("Student Already Enrolled");
                alert.setContentText("This student is already enrolled in the selected course.");
                alert.showAndWait();
            }
            clearInput();

        }
    }

    @FXML
    private void removeFromCourse() {
        CourseWithStudent selectedCourseWithStudent = tableViewCourse.getSelectionModel().getSelectedItem();
        if (selectedCourseWithStudent != null) {
            courseWithStudents.remove(selectedCourseWithStudent);
            institutionManagementCourseWithStudent.remove(selectedCourseWithStudent.getCourseID());
            CourseWithStudentData.removeActiveCourse(selectedCourseWithStudent);
            tableViewCourse.refresh();
        }
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

    private void clearInput(){
        comboBoxStudent.getSelectionModel().clearSelection();
        comboBoxCourse.getSelectionModel().clearSelection();
        txtFullName.clear();
        comboBoxTrainer.getSelectionModel().clearSelection();
        tableViewCourse.getSelectionModel().clearSelection();
        tableViewMakeCourse.getSelectionModel().clearSelection();
    }

    private void loadData(){
        courses.clear();
        courseWithStudents.clear();
        employees.clear();

        courses.addAll(CourseData.getAllCourses());
        courses.forEach(institutionManagementCourse::add);

        courseWithStudents.addAll(CourseWithStudentData.getAllDataFromCourses());
        courseWithStudents.forEach(institutionManagementCourseWithStudent::add);

        employees.addAll(EmployeeData.getAllEmployees());
        comboBoxTrainer.setItems(FXCollections.observableArrayList(employees).sorted());

        comboBoxCourse.setItems(FXCollections.observableArrayList(courses).sorted());

        List<Student> students = StudentData.getAllStudents();
        comboBoxStudent.setItems(FXCollections.observableArrayList(students).sorted());

        tableViewMakeCourse.setItems(courses);
        tableViewCourse.setItems(courseWithStudents);
    }
}
