package com.example.finalproject.model;

import java.io.Serializable;

public class User  implements Serializable {
    protected String id;
    protected  String fname;
    protected String lname;

    protected String phone;
    protected String email;
    protected String password;


    public User(String id, String fname, String lname, String phone, String email, String password) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public User(User theUser) {
        this.id = theUser.getId();
        this.fname = theUser.getFname() ;
        this.lname = theUser.getLname() ;
        this.email = theUser.getEmail() ;

        this.phone = theUser.getPhone() ;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
