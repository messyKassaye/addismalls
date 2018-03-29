package com.example.red.magazine.hotels.models;

/**
 * Created by Meseret on 11/5/2017.
 */

public class Hotels {
    private String name;
    private String photo_path;
    private String company_id;
    private String description;

    public Hotels(String name, String photo_path, String company_id, String description) {
        this.name = name;
        this.photo_path = photo_path;
        this.company_id = company_id;
        this.description = description;
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

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
