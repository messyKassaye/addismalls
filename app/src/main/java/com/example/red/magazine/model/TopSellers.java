package com.example.red.magazine.model;

/**
 * Created by RED on 10/1/2017.
 */

public class TopSellers {
    private int id;
    private String name;
    private String photo_path;
    private String floor_no;
    private String office_no;

    public TopSellers(int id, String name, String photo_path, String floor_no, String office_no) {
        this.id = id;
        this.name = name;
        this.photo_path = photo_path;
        this.floor_no = floor_no;
        this.office_no = office_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
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
