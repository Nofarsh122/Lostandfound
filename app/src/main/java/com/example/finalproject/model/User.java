package com.example.finalproject.model;

import java.io.Serializable;

/// Model class for the user
/// This class represents a user in the application
/// It contains the user's information
/// @see Serializable
public class User implements Serializable {

    /// unique id of the user
    private String id;
    private String email, password;
    private String fname, lname;
    private String phone;
    private boolean isAdmin;

    public User() {
    }

    public User(String uid, String email, String password, String fname, String lname, String phone, boolean isAdmin) {
        this.id = uid;
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }

    public String getUid() {
        return id;
    }

    public void setUid(String uid) {
        this.id = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + fname + '\'' +
                ", LastName='" + lname + '\'' +
                ", phone='" + phone + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        User user = (User) object;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}