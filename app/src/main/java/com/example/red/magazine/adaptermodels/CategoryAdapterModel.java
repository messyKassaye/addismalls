package com.example.red.magazine.adaptermodels;

/**
 * Created by RED on 10/1/2017.
 */

public class CategoryAdapterModel {
    private int id;
    private String name;
    private String photo_path;

    public CategoryAdapterModel() {
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
