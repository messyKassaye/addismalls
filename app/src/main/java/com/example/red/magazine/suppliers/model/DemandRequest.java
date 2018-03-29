package com.example.red.magazine.suppliers.model;

/**
 * Created by RED on 10/12/2017.
 */

public class DemandRequest {
    private String id;
    private String name;
    private String location;
    private String phone;
    private String quantity;


    public DemandRequest(String id, String name, String location, String phone, String quantity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}