package com.bildungsinstitut.database;

import com.bildungsinstitut.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentData {

    public static void addStudent(Student student){
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "INSERT INTO student (id,name,age,email) VALUES (?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, student.getId());
                statement.setString(2,student.getName());
                statement.setInt(3,student.getAge());
                statement.setString(4,student.getEmail());
                statement.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String studentQuery = "SELECT * FROM student";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(studentQuery)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");

                Student student = new Student(id, name, age, email);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void removeStudent(Student student){
        try (Connection connection = DatabaseConnection.getConnection()) {
            // First, we delete the records from the student_course table
            String studentCourseQuery = "DELETE FROM student_course WHERE student_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(studentCourseQuery)) {
                statement.setString(1, student.getId());
                statement.executeUpdate();
            }

            // Then we delete the student from the student table
            String studentQuery = "DELETE FROM student WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(studentQuery)) {
                statement.setString(1, student.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent(Student student){
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "UPDATE student SET name = ?, age = ?, email = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, student.getName());
                statement.setInt(2, student.getAge());
                statement.setString(3, student.getEmail());
                statement.setString(4, student.getId());
                statement.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
