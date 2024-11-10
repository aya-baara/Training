package com.example.CRUDdemo.dao;

import com.example.CRUDdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


public interface EmployeeRepo extends JpaRepository <Employee,Integer>{

// add method to sort by last name
    public List<Employee> findAllByOrderByLastNameAsc();
}
