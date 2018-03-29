package com.example.red.magazine.model;

/**
 * Created by RED on 10/4/2017.
 */

public class AdminUsersDetail {
    private String first_name;
    private String last_name;
    private String mall_name;
    private String photo_path;

    public AdminUsersDetail(String first_name, String last_name, String mall_name, String photo_path) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.mall_name = mall_name;
        this.photo_path = photo_path;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMall_name() {
        return mall_name;
    }

    public void setMall_name(String mall_name) {
        this.mall_name = mall_name;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
