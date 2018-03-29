package com.example.red.magazine.suppliers.model;

/**
 * Created by Meseret on 11/6/2017.
 */

public class Customer {
    private String name;
    private String phone_no;
    private String location;

    public Customer(String name, String phone_no, String location) {
        this.name = name;
        this.phone_no = phone_no;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
