package com.example.red.magazine.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RED on 9/27/2017.
 */

public class Sponser {

    private String name;
    private String photo_path;
    private String company_id;
    private String website_link;

    public Sponser(String name, String photo_path, String company_id, String website_link) {
        this.name = name;
        this.photo_path = photo_path;
        this.company_id = company_id;
        this.website_link = website_link;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }
}
