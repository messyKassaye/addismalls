package com.example.red.magazine.malls.model;

/**
 * Created by RED on 10/2/2017.
 */

public class CategoryModel {
    private int mall_id;
    private String category_type;
    private String category_id;

    public CategoryModel(int mall_id, String category_type, String category_id) {
        this.mall_id = mall_id;
        this.category_type = category_type;
        this.category_id = category_id;
    }

    public int getMall_id() {
        return mall_id;
    }

    public void setMall_id(int mall_id) {
        this.mall_id = mall_id;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
