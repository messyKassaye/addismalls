package com.example.red.magazine.model;

/**
 * Created by RED on 10/4/2017.
 */

public class MallRequestModel {
    private String first_name;
    private String last_name;
    private String mall_name;
    private String company_id;

    public MallRequestModel(String first_name, String last_name, String mall_name, String company_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.mall_name = mall_name;
        this.company_id = company_id;
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

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}
