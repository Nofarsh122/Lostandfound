package com.example.finalproject.model;

public class Item {
    protected String id, desc, date, city, location, conper, status, userId;


    public Item() {
    }

    public Item(String id, String desc, String date, String city, String location, String conper, String status, String userId) {
        this.id = id;
        this.desc = desc;
        this.date = date;
        this.city = city;
        this.location = location;
        this.conper = conper;
        this.status = status;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getConper() {
        return conper;
    }

    public void setConper(String conper) {
        this.conper = conper;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", conper='" + conper + '\'' +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}

