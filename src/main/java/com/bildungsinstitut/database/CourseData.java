package com.bildungsinstitut.database;

import com.bildungsinstitut.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseData {

    public static void addCourse(Course course){
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "INSERT INTO course (id,name,trainerID) VALUES (?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, course.getId());
                statement.setString(2,course.getName());
                statement.setString(3,course.getTrainerID());
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    // Metóda na získanie všetkých kurzov
    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM course";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String trainerID = resultSet.getString("trainerID");
                courses.add(new Course(id, name, trainerID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Metóda na aktualizáciu kurzu
    public static void updateCourse(Course course) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE course SET name = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, course.getName());
                statement.setString(2, course.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metóda na odstránenie kurzu
    public static void deleteCourse(Course course) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM course WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, course.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
