package com.bildungsinstitut.model;

import com.bildungsinstitut.interfaces.Identifiable;

public abstract class Person implements Identifiable {
    protected String id;
    protected String name;
    protected int age;
    protected String email;

    public Person(String id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean hasSameAttributes(Person other) {
        return this.name.equals(other.getName()) &&
                this.age == other.getAge() &&
                this.email.equals(other.getEmail());
    }

    public boolean hasSameEmail(Person other){
        return this.email.equals(other.getEmail());
    }
}
