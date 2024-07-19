package com.bildungsinstitut.model;

import java.util.List;

public class Student extends Person {
    private List<Course> courses;

    public Student(String id, String name, int age, String email,List<Course> courses) {
        super(id, name, age, email);
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
