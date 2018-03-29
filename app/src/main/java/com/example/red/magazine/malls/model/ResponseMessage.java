package com.example.red.magazine.malls.model;

/**
 * Created by RED on 10/3/2017.
 */

public class ResponseMessage {
    private String status;
    private String token;

    public ResponseMessage(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
