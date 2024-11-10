package com.example.advancedMapping.dao;

import com.example.advancedMapping.entity.Course;
import com.example.advancedMapping.entity.Instructor;
import com.example.advancedMapping.entity.InstructorDetail;
import com.example.advancedMapping.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


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

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        TypedQuery<Course> query= entityManager.createQuery(
                "from Course where instructor.id = :data ", Course.class
        );
        query.setParameter("data",id);
        List<Course> mycourses=query.getResultList();
        return mycourses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {
        TypedQuery<Instructor> query= entityManager.createQuery(
                                           "select i from Instructor i " +
                                             "JOIN FETCH i.courses " +
                                                   "JOIN FETCh i.instructorDetail " +
                                             "where i.id=:data ", Instructor.class
        );
        query.setParameter("data",id);
        Instructor temp=query.getSingleResult();
        return temp;
    }

    @Transactional
    @Override
    public void updateInstructor(Instructor instructor) {
        entityManager.merge(instructor);
    }

    @Transactional
    @Override
    public void updateCourse(Course course) {
        entityManager.merge(course);
    }

    @Override
    public Course findCourseById(int id) {
        return entityManager.find(Course.class,id);
    }

    @Transactional
    @Override
    public void deleteInstructorWithoutCourses(int id) {
        Instructor instructor=entityManager.find(Instructor.class,id);
        List<Course> courses=instructor.getCourses();
        for(Course temp:courses){
            temp.setInstructor(null);
        }
        entityManager.remove(instructor);
    }

    @Transactional
    @Override
    public void deleteCourse(int id) {
        Course course=findCourseById(id);
        entityManager.remove(course);
    }

    @Transactional
    @Override
    public void save(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseAndReviewsById(int id) {
        TypedQuery<Course> query=entityManager.createQuery(
                "select c from Course c JOIN FETCH c.reviews where c.id= :data",
                Course.class
        );
        query.setParameter("data",id);
        return query.getSingleResult();
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int id) {
        TypedQuery<Course>query=entityManager.createQuery(
                "select c from Course c JOIN FETCH c.students where c.id= :data"
                , Course.class);
        query.setParameter("data",id);
        return query.getSingleResult();
    }

    @Override
    public Student findStudentAndCourses(int id) {
        TypedQuery<Student >query=entityManager.createQuery(
                "select s from Student s JOIN FETCH s.courses where s.id= :data"
                , Student.class);
        query.setParameter("data",id);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Transactional
    @Override
    public void deleteStudent(int id) {
        Student student=entityManager.find(Student.class,id);
        entityManager.remove(student);
    }
}
