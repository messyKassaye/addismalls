package com.example.red.magazine.malls.model;

/**
 * Created by RED on 10/17/2017.
 */

public class AboutMall {
    private String description;
    private String country;
    private String city;
    private String sub_city;
    private String email;
    private String website;
    private String phone_no;

    public AboutMall(String description, String country, String city, String sub_city, String email, String website, String phone_no) {
        this.description = description;
        this.country = country;
        this.city = city;
        this.sub_city = sub_city;
        this.email = email;
        this.website = website;
        this.phone_no = phone_no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSub_city() {
        return sub_city;
    }

    public void setSub_city(String sub_city) {
        this.sub_city = sub_city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
