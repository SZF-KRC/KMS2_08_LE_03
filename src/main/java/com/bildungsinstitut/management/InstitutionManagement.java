package com.bildungsinstitut.management;

import com.bildungsinstitut.interfaces.Identifiable;
import com.bildungsinstitut.model.Course;
import com.bildungsinstitut.model.Person;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InstitutionManagement<T extends Identifiable> {
    private Map<String, T> items = new HashMap<>();

    public void add(T item) {
        items.put(item.getId(), item);
    }

    public T get(String id) {
        return items.get(id);
    }

    public Collection<T> getAll() {
        return items.values();
    }

    public void remove(String id) {
        items.remove(id);
    }

    public void update(T item) {
        items.put(item.getId(), item);
    }

    public boolean exists(T item) {
        if (item instanceof Person newPerson) {
            for (T existingItem : items.values()) {
                if (existingItem instanceof Person existingPerson) {
                    if (existingPerson.hasSameAttributes(newPerson)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean existsCourse(T item) {
        if (item instanceof Course newCourse) {
            for (T existingItem : items.values()) {
                if (existingItem instanceof Course existingCourse) {
                    if (existingCourse.hasSameAttributes(newCourse)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean existEmail(T item){
        if (item instanceof Person newPerson){
            for (T existingItem : items.values()){
                if (existingItem instanceof Person existingPerson){
                    if (existingPerson.hasSameEmail(newPerson)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
