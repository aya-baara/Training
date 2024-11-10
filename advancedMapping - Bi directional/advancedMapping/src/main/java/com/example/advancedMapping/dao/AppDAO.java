package com.example.advancedMapping.dao;

import com.example.advancedMapping.entity.Instructor;
import com.example.advancedMapping.entity.InstructorDetail;

public interface AppDAO {

    void save(Instructor theInstructor);
    Instructor findInstructorById(int id);
    void deleteInstructor(int id);
    InstructorDetail findInstructorDetailById(int id);
    void deleteInstructorDetailById(int id);
}

