package com.example.CRUDdemo.entity;


import jakarta.persistence.*;

@Entity
@Table(name="employee")
public class Employee
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lasName;

    @Column(name = "email")
    private String email;

    public Employee(){

    }
    public Employee(int id, String firstName, String lasName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lasName = lasName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLasName() {
        return lasName;
    }

    public void setLasName(String lasName) {
        this.lasName = lasName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lasName='" + lasName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
