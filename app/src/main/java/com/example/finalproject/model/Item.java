package com.example.finalproject.model;

public class Item {
    protected String id, desc, date, city, location, phonenum , status, Type, userId, imageBase64;

    // status can be "Not Found" or "Found"

    public Item() {
    }

    public Item(String id, String desc, String date, String city, String location, String phonenum, String status, String type, String userId, String imageBase64) {
        this.id = id;
        this.desc = desc;
        this.date = date;
        this.city = city;
        this.location = location;
        this.phonenum = phonenum;
        this.status = status;
        this.Type = type;
        this.userId = userId;
        this.imageBase64 = imageBase64;
    }

    public Item(Item other) {
        this.id = other.id;
        this.desc = other.desc;
        this.date = other.date;
        this.city = other.city;
        this.location = other.location;
        this.phonenum = other.phonenum;
        this.status = other.status;
        this.Type = other.Type;
        this.userId = other.userId;
        this.imageBase64 = other.imageBase64;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
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

    public String getPhonenum() {
        return phonenum;
    }

    public void setphonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", status='" + status + '\'' +
                ", Type='" + Type + '\'' +
                ", userId='" + userId + '\'' +
//                ", imageBase64='" + imageBase64 + '\'' +
                '}';
    }
}