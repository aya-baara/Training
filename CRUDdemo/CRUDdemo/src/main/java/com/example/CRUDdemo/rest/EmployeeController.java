package com.example.CRUDdemo.rest;


import com.example.CRUDdemo.dao.EmployeeDAO;
import com.example.CRUDdemo.entity.Employee;
import com.example.CRUDdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(){}
    @Autowired
    public EmployeeController (EmployeeService theemployeeService){
        this.employeeService=theemployeeService;
    }
    @GetMapping("/employees")
    public List<Employee>findAll(){
       return employeeService.findAll();
    }

    @GetMapping("/employees/{empId}")
    public Employee getById(@PathVariable int empId){
        Employee emp =employeeService.getById(empId);
        if(emp==null){
            throw new RuntimeException("Employee id not found _"+empId);
        };
        return emp;
    }

    @PostMapping("employees")
    public Employee addEmployee(@RequestBody Employee emp){
        emp.setId(0);  // in case they pass id in json set it to zero to force add new emp not edit
       return employeeService.save(emp);

    }
    @DeleteMapping  ("employees/{empId}")
    public String deleteEmployee(@PathVariable int empId ){
        Employee emp=employeeService.getById(empId);
        if(emp==null){
            throw new RuntimeException("Employee id not found _"+empId);
        }
        employeeService.deleteById(empId);
        return "Deleted successfully";

    }
    @PutMapping("employees")
    public Employee updateEmployee(@RequestBody Employee emp){
        return employeeService.save(emp);
    }
}
