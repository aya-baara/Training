package com.example.CRUDdemo.services;

import com.example.CRUDdemo.dao.EmployeeRepo;
import com.example.CRUDdemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee findById(int theId) {
        return null;
    }

    @Override
    public void save(Employee theEmployee) {
        employeeRepo.save(theEmployee);

    }

    @Override
    public void deleteById(int theId) {

    }
}
