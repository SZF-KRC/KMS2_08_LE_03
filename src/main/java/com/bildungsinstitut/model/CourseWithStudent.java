package com.bildungsinstitut.model;

import com.bildungsinstitut.interfaces.Identifiable;

public class CourseWithStudent implements Identifiable {
    private String courseID;
    private String studentID;

    public CourseWithStudent(String courseID, String studentID) {
        this.courseID = courseID;
        this.studentID = studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getStudentID() {
        return studentID;
    }

  /*  public void setStudentID(String studentID) {
        this.studentID = studentID;
    }*/

    @Override
    public String getId() {
        return "";
    }

    @Override
    public void setId(String id) {

    }
}
