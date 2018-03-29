package com.example.red.magazine.model;

/**
 * Created by RED on 10/4/2017.
 */

public class VerificationItemModel {
    private String role_type;
    private String status;

    public VerificationItemModel(String role_type, String status) {
        this.role_type = role_type;
        this.status = status;
    }

    public String getRole_type() {
        return role_type;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
