package com.example.red.magazine.serviceproviders.model;

/**
 * Created by RED on 10/10/2017.
 */

public class PostResDailyDemand {
    private String id;
    private String quantity;

    public PostResDailyDemand(String id, String quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
