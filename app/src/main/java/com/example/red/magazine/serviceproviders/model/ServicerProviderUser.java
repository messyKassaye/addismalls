package com.example.red.magazine.serviceproviders.model;

/**
 * Created by RED on 10/8/2017.
 */

public class ServicerProviderUser {
    private String first_name;
    private String last_name;
    private String category_id;
    private String name;
    private int floor_no;
    private String office_no;

    public ServicerProviderUser(String first_name, String last_name, String category_id, String name, int floor_no, String office_no) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.category_id = category_id;
        this.name = name;
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

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(int floor_no) {
        this.floor_no = floor_no;
    }

    public String getOffice_no() {
        return office_no;
    }

    public void setOffice_no(String office_no) {
        this.office_no = office_no;
    }
}
