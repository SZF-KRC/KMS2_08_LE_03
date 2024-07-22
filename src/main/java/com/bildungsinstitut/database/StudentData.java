package com.bildungsinstitut.database;

import com.bildungsinstitut.model.Course;
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

           /* // Vloženie záznamov do spojovacej tabuľky student_course
            String studentCourseQuery = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(studentCourseQuery)) {
                for (Course course : student.getCourses()) {
                    statement.setString(1, student.getId());
                    statement.setString(2, course.getId());
                    statement.executeUpdate();
                }
            }*/
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

                // Načítanie kurzov pre každého študenta
                String courseQuery = "SELECT c.id, c.name FROM course c " +
                        "JOIN student_course sc ON c.id = sc.course_id " +
                        "WHERE sc.student_id = ?";
                List<Course> courses = new ArrayList<>();
                try (PreparedStatement courseStatement = connection.prepareStatement(courseQuery)) {
                    courseStatement.setString(1, id);
                    try (ResultSet courseResultSet = courseStatement.executeQuery()) {
                        while (courseResultSet.next()) {
                            String courseId = courseResultSet.getString("id");
                            String courseName = courseResultSet.getString("name");
                            courses.add(new Course(courseId, courseName));
                        }
                    }
                }

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
            // Najprv odstránime záznamy z tabuľky student_course
            String studentCourseQuery = "DELETE FROM student_course WHERE student_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(studentCourseQuery)) {
                statement.setString(1, student.getId());
                statement.executeUpdate();
            }

            // Potom odstránime študenta z tabuľky student
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
