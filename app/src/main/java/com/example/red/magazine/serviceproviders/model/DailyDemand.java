package com.example.red.magazine.serviceproviders.model;

/**
 * Created by RED on 10/10/2017.
 */

public class DailyDemand {
    private String id;
    private String name;
    private String quantity;
    private String created_at;
    private String photo_path;

    public DailyDemand(String id, String name, String quantity, String created_at, String photo_path) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.created_at = created_at;
        this.photo_path = photo_path;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
