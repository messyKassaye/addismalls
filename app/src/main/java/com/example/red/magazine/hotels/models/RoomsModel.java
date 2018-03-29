package com.example.red.magazine.hotels.models;

/**
 * Created by Meseret on 11/11/2017.
 */

public class RoomsModel {
    private String id;
    private String title;
    private String description;
    private String price;
    private String cover;

    public RoomsModel(String id, String title, String description, String price, String cover) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
