package com.example.red.magazine.topsellers.model;

/**
 * Created by RED on 10/3/2017.
 */

public class TopSellersItem {
    private int id;
    private String caption;
    private String product_photo;
    private String price;

    public TopSellersItem(int id, String caption, String product_photo, String price) {
        this.id = id;
        this.caption = caption;
        this.product_photo = product_photo;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
