package com.bildungsinstitut.view;

import com.bildungsinstitut.database.EmployeeData;
import com.bildungsinstitut.management.InstitutionManagement;
import com.bildungsinstitut.model.Employee;
import com.bildungsinstitut.util.UniqueID;
import com.bildungsinstitut.util.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class EmployeeWindowController {

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TableColumn<Employee, String> colName;

    @FXML
    private TableColumn<Employee, Integer> colAge;

    @FXML
    private TableColumn<Employee, String> colEmail;

    @FXML
    private TableColumn<Employee, String> colPosition;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtPosition;

    private final ObservableList<Employee> employeeList = FXCollections.observableArrayList();
    private final InstitutionManagement<Employee> institutionManagement = new InstitutionManagement<>();

    @FXML
    public void initialize() {
        setupButtonHandlers(btnAdd);
        setupButtonHandlers(btnRemove);
        setupButtonHandlers(btnUpdate);

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

        tableView.setItems(employeeList);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtFullName.setText(newValue.getName());
                txtAge.setText(String.valueOf(newValue.getAge()));
                txtEmail.setText(newValue.getEmail());
                txtPosition.setText(newValue.getPosition());
            }
        });
        tableView.setRowFactory(tv -> new TableRow<Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
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
        tableView.setPlaceholder(stackPane);
        loadEmployees();
    }

    @FXML
    private void addEmployee(){
        if (ValidationUtil.validateInputsEmployee(txtFullName,txtEmail,txtAge,txtPosition)){
            String fullName = txtFullName.getText().trim();
            String email = txtEmail.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            String position = txtPosition.getText().trim();

            Employee employee = new Employee(Id(),fullName,age,email,position);

            if (institutionManagement.exists(employee)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Duplicate Employee");
                alert.setHeaderText("Employee Already Exists");
                alert.setContentText("An employee with the same details already exists. Do you want to add this employee anyway?");

                if (alert.showAndWait().get() != ButtonType.OK) {
                    return;
                }
            }

            if (institutionManagement.existEmail(employee)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Duplicate Email");
                alert.setHeaderText("Employee Email Already Exists");
                alert.setContentText("An employee with the same email already exists. Do you want to add this employee anyway?");

                if (alert.showAndWait().get() != ButtonType.OK) {
                    return;
                }
            }

            employeeList.add(employee);
            EmployeeData.addEmployee(employee);
            institutionManagement.add(employee);

        }
    }

    private String Id(){
        return UniqueID.generateUniqueID();
    }


    @FXML
    private void updateEmployee() {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null && ValidationUtil.validateInputsEmployee(txtFullName,txtEmail,txtAge,txtPosition)) {
            selectedEmployee.setName(txtFullName.getText().trim());
            selectedEmployee.setAge(Integer.parseInt(txtAge.getText().trim()));
            selectedEmployee.setEmail(txtEmail.getText().trim());
            selectedEmployee.setPosition(txtPosition.getText().trim());

            tableView.refresh();
            institutionManagement.update(selectedEmployee);
            EmployeeData.updateEmployee(selectedEmployee);
        }
    }

    @FXML
    private void removeEmployee() {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeList.remove(selectedEmployee);
            institutionManagement.remove(selectedEmployee.getId());
            EmployeeData.removeEmployee(selectedEmployee);
        }
    }

    private void loadEmployees() {
        employeeList.addAll(EmployeeData.getAllEmployees());
        employeeList.forEach(institutionManagement::add);
    }

    private void setupButtonHandlers(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> button.setStyle("-fx-background-color: #c3d8ff; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> button.setStyle("-fx-background-color: #74BFcf; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> button.setStyle("-fx-background-color: #638ac8; -fx-font-weight: bold; -fx-font-size: 12px;"));
        button.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> button.setStyle("-fx-background-color: #c3d8ff; -fx-font-weight: bold; -fx-font-size: 12px;"));
    }
}
