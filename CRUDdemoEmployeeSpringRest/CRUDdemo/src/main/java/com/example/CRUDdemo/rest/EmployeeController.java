package com.example.CRUDdemo.rest;


import com.example.CRUDdemo.entity.Employee;
import com.example.CRUDdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController() {
    }

    @Autowired
    public EmployeeController(EmployeeService theemployeeService) {
        this.employeeService = theemployeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model theModel) {

        // get the employees from db
        List<Employee> theEmployees = employeeService.findAll();
        for(Employee e: theEmployees){
            System.out.println(e.toString());
        }
        // add to the spring model
        theModel.addAttribute("employees", theEmployees);

        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showForm(Model theModel){
        Employee theEmployee=new Employee();
        theModel.addAttribute("employee",theEmployee);
        return "employees/employee-form";

    }

    @PostMapping("/save")
    public String addNewEmployeeFromForm(@ModelAttribute("employee")Employee theEmployee ){
        employeeService.save(theEmployee);

        return "redirect:/api/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showUpdateForm(Model theModel,@RequestParam("employeeId") int theId){
        Employee emp=employeeService.getById(theId);
        theModel.addAttribute("employee",emp);

        return "employees/employee-form";
    }
    @GetMapping("delete")
    public String deleteEmployee(Model theModel,@RequestParam("employeeId")int theId){
        Employee emp=employeeService.getById(theId);
        employeeService.deleteById(theId);
        return "redirect:/api/list";

    }

}
//    @GetMapping("/employees")
//    public List<Employee>findAll(){
//       return employeeService.findAll();
//    }
//
//    @GetMapping("/employees/{empId}")
//    public Employee getById(@PathVariable int empId){
//        Employee emp =employeeService.getById(empId);
//        if(emp==null){
//            throw new RuntimeException("Employee id not found _"+empId);
//        };
//        return emp;
//    }
//
//    @PostMapping("employees")
//    public Employee addEmployee(@RequestBody Employee emp){
//        emp.setId(0);  // in case they pass id in json set it to zero to force add new emp not edit
//       return employeeService.save(emp);
//
//    }
//    @DeleteMapping  ("employees/{empId}")
//    public String deleteEmployee(@PathVariable int empId ){
//        Employee emp=employeeService.getById(empId);
//        if(emp==null){
//            throw new RuntimeException("Employee id not found _"+empId);
//        }
//        employeeService.deleteById(empId);
//        return "Deleted successfully";
//
//    }
//    @PutMapping("employees")
//    public Employee updateEmployee(@RequestBody Employee emp){
//        return employeeService.save(emp);
//    }

