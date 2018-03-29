package com.example.red.magazine.malls.model;

/**
 * Created by RED on 10/13/2017.
 */

public class ManageRetailersModel {
    private String photo_path;
    private String name;
    private String office_no;

    public ManageRetailersModel(String photo_path, String name, String office_no) {
        this.photo_path = photo_path;
        this.name = name;
        this.office_no = office_no;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice_no() {
        return office_no;
    }

    public void setOffice_no(String office_no) {
        this.office_no = office_no;
    }
}
