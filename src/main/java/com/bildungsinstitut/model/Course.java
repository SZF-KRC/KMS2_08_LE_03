package com.bildungsinstitut.model;

import com.bildungsinstitut.database.EmployeeData;
import com.bildungsinstitut.interfaces.Identifiable;

import java.util.List;

public class Course implements Identifiable {
    private String id;
    private String name;
    private String trainerID;

    public Course(String id, String name, String trainerID) {
        this.id = id;
        this.name = name;
        this.trainerID = trainerID;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(String trainerID) {
        this.trainerID = trainerID;
    }

    public String getTrainerName(List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getId().equals(this.trainerID)) {
                return employee.getName();
            }
        }
        return "Unknown";
    }

    public boolean hasSameAttributes(Course other) {
        return this.name.equals(other.getName()) &&
                this.trainerID.equals(other.getTrainerID());
    }


    @Override
    public String toString() {
        return name + " - " + getTrainerName(EmployeeData.getAllEmployees());
    }

}
