package com.example.red.magazine.malls.model;

/**
 * Created by RED on 10/17/2017.
 */

public class Event {
    private String company_name;
    private String event_title;
    private String description;
    private String photo_path;
    private String starting_date;
    private String ending_date;

    public Event(String company_name, String event_title, String description, String photo_path, String starting_date, String ending_date) {
        this.company_name = company_name;
        this.event_title = event_title;
        this.description = description;
        this.photo_path = photo_path;
        this.starting_date = starting_date;
        this.ending_date = ending_date;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
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

    public String getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }

    public String getEnding_date() {
        return ending_date;
    }

    public void setEnding_date(String ending_date) {
        this.ending_date = ending_date;
    }
}
