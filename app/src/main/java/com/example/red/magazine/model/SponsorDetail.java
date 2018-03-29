package com.example.red.magazine.model;

/**
 * Created by Meseret on 1/4/2018.
 */

public class SponsorDetail {
    private String name;
    private String description;
    private String photo_path;


    public SponsorDetail(String name, String description, String photo_path) {
        this.name = name;
        this.description = description;
        this.photo_path = photo_path;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
