package com.example.red.magazine.hotels.models;

/**
 * Created by Meseret on 11/18/2017.
 */

public class OpeningHours {
    private String monday;
    private String tuesday;
    private String wendsday;
    private String thursday;
    private String friday;
    private String satureday;
    private String sunday;

    public OpeningHours(String monday, String tuesday, String wendsday, String thursday, String friday, String satureday, String sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wendsday = wendsday;
        this.thursday = thursday;
        this.friday = friday;
        this.satureday = satureday;
        this.sunday = sunday;
    }

    public String getSatureday() {
        return satureday;
    }

    public void setSatureday(String satureday) {
        this.satureday = satureday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWendsday() {
        return wendsday;
    }

    public void setWendsday(String wendsday) {
        this.wendsday = wendsday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }
}
