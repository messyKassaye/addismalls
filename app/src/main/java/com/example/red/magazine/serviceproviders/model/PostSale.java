package com.example.red.magazine.serviceproviders.model;

/**
 * Created by RED on 10/13/2017.
 */

public class PostSale {
    private String caption;
    private String price;
    private String product_photo;

    public PostSale(String caption, String price, String product_photo) {
        this.caption = caption;
        this.price = price;
        this.product_photo = product_photo;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }
}
