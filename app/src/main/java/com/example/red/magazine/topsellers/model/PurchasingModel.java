package com.example.red.magazine.topsellers.model;

/**
 * Created by Meseret on 1/13/2018.
 */

public class PurchasingModel {
    private String name;
    private String caption;
    private String product_photo;
    private String price;
    private String description;

    public PurchasingModel(String name, String caption, String product_photo, String price, String description) {
        this.name = name;
        this.caption = caption;
        this.product_photo = product_photo;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
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
}
