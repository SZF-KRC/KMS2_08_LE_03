package com.bildungsinstitut.model;

public class Employee extends Person{
private String position;


    public Employee(String id, String name, int age, String email, String position) {
        super(id, name, age, email); // Volanie konštruktora nadtriedy
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
