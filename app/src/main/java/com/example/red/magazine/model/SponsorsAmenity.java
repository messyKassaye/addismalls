package com.example.red.magazine.model;

/**
 * Created by Meseret on 1/4/2018.
 */

public class SponsorsAmenity {
    private String name;
    private String description;

    public SponsorsAmenity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
