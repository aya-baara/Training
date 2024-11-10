package com.example.codedemo.entity;

public class StudentV2 {

    private String firstName;
    private String lasName;

    public StudentV2(){

    }

    public StudentV2(String firstName, String lasName) {
        this.firstName = firstName;
        this.lasName = lasName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLasName() {
        return lasName;
    }

    public void setLasName(String lasName) {
        this.lasName = lasName;
    }
}
