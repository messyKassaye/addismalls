package com.example.red.magazine.malls.model;

/**
 * Created by RED on 10/8/2017.
 */

public class NewRetailers {
    private String password;
    private String tin_no;

    public NewRetailers(String password, String tin_no) {
        this.password = password;
        this.tin_no = tin_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTin_no() {
        return tin_no;
    }

    public void setTin_no(String tin_no) {
        this.tin_no = tin_no;
    }
}
