package com.bildungsinstitut.database;

import com.bildungsinstitut.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeData {

    public static void addEmployee(Employee employee){
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "INSERT INTO employee (id,name,age,email,position) VALUES (?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, employee.getId());
                statement.setString(2,employee.getName());
                statement.setInt(3,employee.getAge());
                statement.setString(4,employee.getEmail());
                statement.setString(5,employee.getPosition());
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employee";

        try (Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String position = resultSet.getString("position");

                Employee employee = new Employee(id,name,age,email,position);
                employees.add(employee);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }

    public static void removeEmployee(Employee employee){
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "DELETE FROM employee WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, employee.getId());
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE employee SET name = ?, age = ?, email = ?, position = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, employee.getName());
                statement.setInt(2, employee.getAge());
                statement.setString(3, employee.getEmail());
                statement.setString(4, employee.getPosition());
                statement.setString(5, employee.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
