package com.bildungsinstitut.database;

import com.bildungsinstitut.model.Course;
import com.bildungsinstitut.model.CourseWithStudent;
import com.bildungsinstitut.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseWithStudentData {

    public static void addStudentToCourse(Student student, Course course){
        try(Connection connection = DatabaseConnection.getConnection()){
            String query = "INSERT INTO student_course (student_id,course_id) VALUES (?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, student.getId());
                statement.setString(2,course.getId());

                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static List<CourseWithStudent> getAllDataFromCourses(){
        List<CourseWithStudent> courses = new ArrayList<>();
        String query = "SELECT * FROM student_course";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()){
                String student_id = resultSet.getString("student_id");
                String course_id = resultSet.getString("course_id");
                CourseWithStudent course = new CourseWithStudent(course_id,student_id);

                courses.add(course);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return courses;
    }

    public static void removeActiveCourse(CourseWithStudent activeCourse){
        try (Connection connection = DatabaseConnection.getConnection()){
            String query = "DELETE FROM student_course WHERE student_id = ? AND course_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, activeCourse.getStudentID());
                statement.setString(2, activeCourse.getCourseID());
                statement.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
