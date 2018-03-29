package com.example.red.magazine.serviceproviders.model;

/**
 * Created by RED on 10/9/2017.
 */

public class Demand {
    private String photo_path;
    private String name;
    private String product_photo;
    private String availability;
    private String quantity;
    private String price;
    private String description;
    private String time;

    public Demand(String photo_path, String name, String product_photo, String availability, String quantity, String price, String description, String time) {
        this.photo_path = photo_path;
        this.name = name;
        this.product_photo = product_photo;
        this.availability = availability;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.time = time;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
