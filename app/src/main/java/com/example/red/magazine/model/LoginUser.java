package com.example.red.magazine.model;

/**
 * Created by RED on 10/4/2017.
 */

public class LoginUser {
    private String tin_no;
    private String password;

    public LoginUser(String tin_no, String password) {
        this.tin_no = tin_no;
        this.password = password;
    }

    public String getTin_no() {
        return tin_no;
    }

    public void setTin_no(String tin_no) {
        this.tin_no = tin_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
