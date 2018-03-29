package com.example.red.magazine.hotels.models;

/**
 * Created by Meseret on 11/18/2017.
 */

public class HotelDisheModel {
    private String name;
    private String photo_path;
    private String description;

    public HotelDisheModel(String name, String photo_path, String description) {
        this.name = name;
        this.photo_path = photo_path;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
