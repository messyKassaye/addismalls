package com.example.red.magazine.cafeandres.model;

/**
 * Created by Meseret on 12/17/2017.
 */

public class CafeResDishes {
    private String name;
    private String photo_path;
    private String description;
    private String price;

    public CafeResDishes(String name, String photo_path, String description, String price) {
        this.name = name;
        this.photo_path = photo_path;
        this.description = description;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
