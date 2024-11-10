package com.example.CRUDdemo.dao;

import com.example.CRUDdemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    EntityManager entityManager;

    @Autowired
    public EmployeeDAOJpaImpl(EntityManager em){
        entityManager=em;
    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee>theQuery=entityManager.createQuery("from Employee",Employee.class);
        List<Employee>employees=theQuery.getResultList();
        return employees;
    }

    @Override
    public Employee getById(int id) {
        return entityManager.find(Employee.class,id);

    }

    @Override
    public Employee save(Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    public void deleteById(int id) {
        Employee emp=getById(id);
        entityManager.remove(emp);

    }
}
