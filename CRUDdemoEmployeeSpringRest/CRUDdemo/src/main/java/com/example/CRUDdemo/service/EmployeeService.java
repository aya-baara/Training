package com.example.CRUDdemo.service;

import com.example.CRUDdemo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee>findAll();
    Employee getById(int id);
    Employee save(Employee employee);
    void deleteById(int id);
}
