package com.example.CRUDdemo.dao;

import com.example.CRUDdemo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> findAll();
    Employee getById(int id);
    Employee save(Employee employee);
    void deleteById(int id);

}
