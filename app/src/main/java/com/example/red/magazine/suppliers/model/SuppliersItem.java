package com.example.red.magazine.suppliers.model;

/**
 * Created by RED on 10/12/2017.
 */

public class SuppliersItem {
    private String id;
    private String name;

    public SuppliersItem(String id, String name) {
        this.id = id;
        this.name = name;
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
}
