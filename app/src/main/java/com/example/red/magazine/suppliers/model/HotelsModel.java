package com.example.red.magazine.suppliers.model;

/**
 * Created by Meseret on 11/6/2017.
 */

public class HotelsModel {
    private String id;
    private String name;
    private String photo_path;

    public HotelsModel(String id, String name, String photo_path) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
