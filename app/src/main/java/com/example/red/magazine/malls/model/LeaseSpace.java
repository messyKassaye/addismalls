package com.example.red.magazine.malls.model;

/**
 * Created by RED on 10/13/2017.
 */

public class LeaseSpace {
    private String id;
    private String photo_path;
    private String description;

    public LeaseSpace(String id, String photo_path, String description) {
        this.id = id;
        this.photo_path = photo_path;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
