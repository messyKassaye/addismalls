package com.example.red.magazine.model;

/**
 * Created by Meseret on 1/4/2018.
 */

public class Sponsors_branch {
    private String name;
    private String photo_path;

    public Sponsors_branch(String name, String photo_path) {
        this.name = name;
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
}
