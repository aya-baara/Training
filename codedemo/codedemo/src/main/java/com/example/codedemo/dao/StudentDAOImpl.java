package com.example.codedemo.dao;

import com.example.codedemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class StudentDAOImpl implements StudentDAO{
    private EntityManager entityManager;

    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void save(Student theStudent) {
        entityManager.persist(theStudent);

    }


    /// didn't add @Transactional because it is only reading there is no action to do
    @Override
    public Student findById(Integer id) {
        return entityManager.find(Student.class,id);
    }

    @Override
    public List<Student> findALL() {
        TypedQuery<Student> theQuery=entityManager.createQuery("from Student order by lastName desc",Student.class);
        return theQuery.getResultList();

    }

    @Override
    public List<Student> findByLastName(String lasName) {
        TypedQuery<Student> theQuery=entityManager.createQuery("from Student order WHERE lastName = :value ",Student.class);
        theQuery.setParameter("value",lasName);
        return theQuery.getResultList();

    }

    @Transactional
    @Override
    public void update(Student theStudent) {
        entityManager.merge(theStudent);
    }

    @Transactional
    @Override
    public void delete(int id) {
        Student student=this.findById(id);
        entityManager.remove(student);
    }
}
