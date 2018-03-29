package com.example.red.magazine.model;

/**
 * Created by RED on 10/5/2017.
 */

public class MallRequestAllowModel {
    private String company_id;

    public MallRequestAllowModel(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}
