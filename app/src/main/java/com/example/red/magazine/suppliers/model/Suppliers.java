package com.example.red.magazine.suppliers.model;

/**
 * Created by RED on 10/12/2017.
 */

public class Suppliers {
    private String first_name;
    private String last_name;
    private String name;

    public Suppliers(String first_name, String last_name, String name) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
