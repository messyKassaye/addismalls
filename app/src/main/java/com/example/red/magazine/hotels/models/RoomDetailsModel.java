package com.example.red.magazine.hotels.models;

/**
 * Created by Meseret on 11/11/2017.
 */

public class RoomDetailsModel {
    private String id;
    private String title;
    private String sub_title;
    private String description;
    private String price;
    private String no_rooms;
    private String accommodation;
    private String cover;

    public RoomDetailsModel(String id, String title, String sub_title, String description, String price, String no_rooms, String accommodation, String cover) {
        this.id = id;
        this.title = title;
        this.sub_title = sub_title;
        this.description = description;
        this.price = price;
        this.no_rooms = no_rooms;
        this.accommodation = accommodation;
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNo_rooms() {
        return no_rooms;
    }

    public void setNo_rooms(String no_rooms) {
        this.no_rooms = no_rooms;
    }

    public String getAccomodation() {
        return accommodation;
    }

    public void setAccomodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
