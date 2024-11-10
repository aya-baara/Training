package com.example.codedemo.rest;


import com.example.codedemo.entity.Student;
import com.example.codedemo.entity.StudentV2;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    private List<StudentV2>students=new ArrayList<>();
    @PostConstruct
    public void loadData(){
      students=new ArrayList<>();
        students.add(new StudentV2("sami","smam"));
        students.add(new StudentV2("Aya","Jamal"));
        students.add(new StudentV2("marwa","Naser"));


    }

    @GetMapping("/students")
    public List<StudentV2> getAllSteStudentV2dents(){
        return students;
    }


    @GetMapping("/students/{studentId}")
    public StudentV2 getStudent (@PathVariable int studentId){

        if(studentId>students.size()||studentId<0){
            throw  new StudentNotFoundException("Student id not found _"+studentId);

        }

        return students.get(studentId);
    }




}
