package com.example.CRUDdemo.service;

import com.example.CRUDdemo.dao.EmployeeRepo;
import com.example.CRUDdemo.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo){
        this.employeeRepo=employeeRepo;
    }
    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAllByOrderByLastNameAsc();
    }

    @Override
    public Employee getById(int id) {
        Optional<Employee> result = (employeeRepo.findById(id));
        Employee emp=null;
        if(result.isPresent()){
            emp=result.get();

        }
        else{
            throw new RuntimeException("didn't find employee id_"+id);
        }
        return emp;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public void deleteById(int id) {
        employeeRepo.deleteById(id);
    }
}
