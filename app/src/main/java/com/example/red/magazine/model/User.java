package com.example.red.magazine.model;

/**
 * Created by RED on 10/3/2017.
 */

public class User {
    private String first_name,last_name,phone_no;
    private String mall_name,tin_no,city,floors_no;
    private String password;

    public User(String first_name, String last_name, String phone_no, String mall_name, String tin_no, String city, String floors_no, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_no = phone_no;
        this.mall_name = mall_name;
        this.tin_no = tin_no;
        this.city = city;
        this.floors_no = floors_no;
        this.password = password;
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

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getMall_name() {
        return mall_name;
    }

    public void setMall_name(String mall_name) {
        this.mall_name = mall_name;
    }

    public String getTin_no() {
        return tin_no;
    }

    public void setTin_no(String tin_no) {
        this.tin_no = tin_no;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFloors_no() {
        return floors_no;
    }

    public void setFloors_no(String floors_no) {
        this.floors_no = floors_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
