package com.example.red.magazine.serviceproviders.model;

/**
 * Created by RED on 10/11/2017.
 */

public class PostDishe {
    private String id;
    private String name;
    private String price;
    private String photo_path;
    private String created_at;

    public PostDishe(String id, String name, String price, String photo_path, String created_at) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.photo_path = photo_path;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
