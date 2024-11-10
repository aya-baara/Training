package com.example.advancedMapping.dao;

import com.example.advancedMapping.entity.Course;
import com.example.advancedMapping.entity.Instructor;
import com.example.advancedMapping.entity.InstructorDetail;

import java.util.List;

public interface AppDAO {

    void save(Instructor theInstructor);
    Instructor findInstructorById(int id);
    void deleteInstructor(int id);
    InstructorDetail findInstructorDetailById(int id);
    void deleteInstructorDetailById(int id);
    List<Course>findCoursesByInstructorId(int id);
    Instructor findInstructorByIdJoinFetch(int id);
    void updateInstructor(Instructor instructor);
    void updateCourse(Course course);
    Course findCourseById(int id);
    void deleteInstructorWithoutCourses(int id);
    void deleteCourse(int id);
}

