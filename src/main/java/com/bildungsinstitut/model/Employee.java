package com.bildungsinstitut.model;

public class Employee extends Person {
private String position;


    public Employee(String id, String name, int age, String email, String position) {
        super(id, name, age, email);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean hasSameAttributes(Person other) {
        if (other instanceof Employee) {
            Employee otherEmployee = (Employee) other;
            return super.hasSameAttributes(other) &&
                    this.position.equals(otherEmployee.getPosition());
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
