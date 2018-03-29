package com.example.red.magazine.serviceproviders.model;

/**
 * Created by RED on 10/9/2017.
 */

public class RetailerProfile {
    private String first_name;
    private String last_name;
    private String name;
    private String category_type;
    private String floor_no;
    private String office_no;

    public RetailerProfile(String first_name, String last_name, String name, String category_type, String floor_no, String office_no) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.name = name;
        this.category_type = category_type;
        this.floor_no = floor_no;
        this.office_no = office_no;
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

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(String floor_no) {
        this.floor_no = floor_no;
    }

    public String getOffice_no() {
        return office_no;
    }

    public void setOffice_no(String office_no) {
        this.office_no = office_no;
    }
}
