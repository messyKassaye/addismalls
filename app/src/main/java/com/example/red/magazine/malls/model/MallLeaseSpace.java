package com.example.red.magazine.malls.model;

/**
 * Created by ezedin on 10/29/17.
 */

public class MallLeaseSpace {
    private String floor_no;
    private String area;
    private String purpose;
    private String description;
    private String photo_path;
    private String phone_no;

    public MallLeaseSpace(String floor_no, String area, String purpose, String description, String photo_path, String phone_no) {
        this.floor_no = floor_no;
        this.area = area;
        this.purpose = purpose;
        this.description = description;
        this.photo_path = photo_path;
        this.phone_no = phone_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(String floor_no) {
        this.floor_no = floor_no;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
