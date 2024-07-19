package com.bildungsinstitut.management;

import com.bildungsinstitut.interfaces.Identifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InstitutionManagement<T extends Identifiable> {
    private Map<String, T> items = new HashMap<>();

    public void add(T item) {
        items.put(item.getId(), item);
    }

  /*  public T get(String id) {
        return items.get(id);
    } */

    public T get(T item) {
        return items.get(item.getId());
    }

    public Collection<T> getAll() {
        return items.values();
    }

    /*public void remove(String id) {
        items.remove(id);
    }*/
    public void remove(T item) {
        items.remove(item.getId(),item);
    }

    public void update(T item) {
        items.put(item.getId(), item);
    }
}
