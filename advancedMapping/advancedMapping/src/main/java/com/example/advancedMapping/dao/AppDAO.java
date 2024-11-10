package com.example.advancedMapping.dao;

import com.example.advancedMapping.entity.Instructor;

public interface AppDAO {

    void save(Instructor theInstructor);
    Instructor findInstructorById(int id);
    void deleteInstructor(int id);
}
