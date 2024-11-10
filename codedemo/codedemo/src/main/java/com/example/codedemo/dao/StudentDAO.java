package com.example.codedemo.dao;

import com.example.codedemo.entity.Student;

import java.util.List;

public interface StudentDAO {

    void save(Student theStudent);
    Student findById(Integer id);
    List<Student> findALL();
    List<Student> findByLastName(String lasName);
    void update(Student theStudent);
    void delete(int id);

}
