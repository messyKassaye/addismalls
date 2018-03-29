package com.example.red.magazine.serviceproviders.model;

/**
 * Created by RED on 10/10/2017.
 */

public class ResItem {
    private String name;
    private String id;

    public ResItem(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
