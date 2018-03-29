package com.example.red.magazine.cafeandres.model;

/**
 * Created by Meseret on 12/17/2017.
 */

public class CafeAndResMenu {
    private String id;
    private String photo_path;

    public CafeAndResMenu(String id, String photo_path) {
        this.id = id;
        this.photo_path = photo_path;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
