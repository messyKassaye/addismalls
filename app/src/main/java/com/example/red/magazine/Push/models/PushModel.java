package com.example.red.magazine.Push.models;

/**
 * Created by Meseret on 11/6/2017.
 */

public class PushModel {
    private String id;
    private String name;

    public PushModel(String id, String name) {
        this.id = id;
        this.name = name;
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
}
