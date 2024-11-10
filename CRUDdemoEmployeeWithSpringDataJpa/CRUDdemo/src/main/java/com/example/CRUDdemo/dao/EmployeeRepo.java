package com.example.CRUDdemo.dao;

import com.example.CRUDdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "members")
public interface EmployeeRepo extends JpaRepository <Employee,Integer>{
}
