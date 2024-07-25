package com.bildungsinstitut.model;

public class Student extends Person {

    public Student(String id, String name, int age, String email) {
        super(id, name, age, email);
    }

    @Override
    public String toString() {
        return name;
    }
}
