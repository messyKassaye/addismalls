package com.example.red.magazine.Push.models;

/**
 * Created by Meseret on 11/6/2017.
 */

public class PushPostModel {
    private String referal_code;
    private String brande_name_id;
    private String quantity;

    public PushPostModel(String referal_code, String brande_name_id, String quantity) {
        this.referal_code = referal_code;
        this.brande_name_id = brande_name_id;
        this.quantity = quantity;
    }

    public String getReferal_code() {
        return referal_code;
    }

    public void setReferal_code(String referal_code) {
        this.referal_code = referal_code;
    }

    public String getBrande_name_id() {
        return brande_name_id;
    }

    public void setBrande_name_id(String brande_name_id) {
        this.brande_name_id = brande_name_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
