package com.ciuciu.footballhighlight.model;

import java.util.ArrayList;
import java.util.List;

public class ItemList<T> {
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void clear() {
        items.clear();
    }

    public int size() {
        return items.size();
    }

    public void addAll(List<T> list) {
        items.addAll(list);
    }

    public void add(T t) {
        if (t != null)
            items.add(t);
    }

    public void add(int index, T t) {
        items.add(index, t);
    }

    public List<T> list() {
        return items;
    }

    public ItemList(List<T> items) {
        this.items = items;
    }

    public ItemList() {
        this.items = new ArrayList<>();
    }

    public T get(int index) {
        return items.get(index);
    }
}
