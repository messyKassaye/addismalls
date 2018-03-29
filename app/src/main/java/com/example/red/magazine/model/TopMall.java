package com.example.red.magazine.model;

/**
 * Created by RED on 10/1/2017.
 */

public class TopMall {
    private String company_id;
    private String name;
    private String photo_path;

    public TopMall(String company_id, String name, String photo_path) {
        this.company_id = company_id;
        this.name = name;
        this.photo_path = photo_path;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
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
}
