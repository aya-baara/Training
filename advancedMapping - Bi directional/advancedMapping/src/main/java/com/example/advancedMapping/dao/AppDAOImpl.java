package com.example.advancedMapping.dao;

import com.example.advancedMapping.entity.Instructor;
import com.example.advancedMapping.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class AppDAOImpl implements AppDAO{

    EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);

    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class,id);
    }

    @Override
    @Transactional
    public void deleteInstructor(int id) {
        Instructor instructor=findInstructorById(id);
        entityManager.remove(instructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return entityManager.find(InstructorDetail.class,id);
    }

    @Transactional
    @Override
    public void deleteInstructorDetailById(int id) {
        InstructorDetail temp =findInstructorDetailById(id);
        temp.getInstructor().setInstructorDetail(null);
        entityManager.remove(temp);

    }
}