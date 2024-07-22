package com.bildungsinstitut.view;

import com.bildungsinstitut.database.StudentData;
import com.bildungsinstitut.management.InstitutionManagement;
import com.bildungsinstitut.model.Student;
import com.bildungsinstitut.util.UniqueID;
import com.bildungsinstitut.util.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class StudentWindowController {
    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAge;

    @FXML
    private TableView<Student> tableView;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Student, String> coName;

    @FXML
    private TableColumn<Student, Integer> colAge;

    @FXML
    private TableColumn<Student, String> colEmail;

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();
    private final InstitutionManagement<Student> institutionManagement = new InstitutionManagement<>();

    @FXML
    public void initialize() {
        setupButtonHandlers(btnAdd);
        setupButtonHandlers(btnRemove);
        setupButtonHandlers(btnUpdate);

        coName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableView.setItems(studentList);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtFullName.setText(newValue.getName());
                txtAge.setText(String.valueOf(newValue.getAge()));
                txtEmail.setText(newValue.getEmail());
            }
        });

        tableView.setRowFactory(tv -> new TableRow<Student>() {
            @Override
            protected void updateItem(Student item, boolean empty) {
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
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #E0F7FA;");
        tableView.setPlaceholder(stackPane);

        loadStudents();
    }


    @FXML
    private void addStudent(){
        if (ValidationUtil.validateInputsStudent(txtFullName,txtEmail,txtAge));{
            String fullName = txtFullName.getText().trim();
            String email = txtEmail.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());

            Student student = new Student(Id(),fullName,age,email);

            if (institutionManagement.exists(student)){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Duplicate Student");
                alert.setHeaderText("Student Already Exists");
                alert.setContentText("An student with the same details already exists. Do you want to add this student anyway?");

                if (alert.showAndWait().get() != ButtonType.OK) {
                    return;
                }
            }

            if (institutionManagement.existEmail(student)){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Duplicate Email");
                alert.setHeaderText("Student Email Already Exists");
                alert.setContentText("An student with the same email already exists. Do you want to add this student anyway?");

                if (alert.showAndWait().get() != ButtonType.OK) {
                    return;
                }
            }

            studentList.add(student);
            StudentData.addStudent(student);
            institutionManagement.add(student);
        }
    }

    @FXML
    private void updateStudent() {
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null && ValidationUtil.validateInputsStudent(txtFullName, txtEmail, txtAge)) {
            selectedStudent.setName(txtFullName.getText().trim());
            selectedStudent.setAge(Integer.parseInt(txtAge.getText().trim()));
            selectedStudent.setEmail(txtEmail.getText().trim());

            tableView.refresh();
            institutionManagement.update(selectedStudent);
            StudentData.updateStudent(selectedStudent);
        }
    }

    @FXML
    private void removeStudent(){
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent !=null){
            studentList.remove(selectedStudent);
            institutionManagement.remove(selectedStudent.getId());
            StudentData.removeStudent(selectedStudent);
        }
    }

    private void loadStudents(){
        studentList.addAll(StudentData.getAllStudents());
        studentList.forEach(institutionManagement::add);
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
