package com.example.red.magazine.malls.model;

/**
 * Created by RED on 10/2/2017.
 */

public class SingleMallsModel {
    private int id;
    private String name;
    private String photo_path;

    public SingleMallsModel(int id, String name, String photo_path) {
        this.id = id;
        this.name = name;
        this.photo_path = photo_path;
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
}
