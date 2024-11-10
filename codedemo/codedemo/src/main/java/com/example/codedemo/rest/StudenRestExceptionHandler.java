package com.example.codedemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudenRestExceptionHandler {
    //add exception handler
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException (StudentNotFoundException e){

        StudentErrorResponse er=new StudentErrorResponse();
        er.setStatus(HttpStatus.NOT_FOUND.value());
        er.setMessage(e.getMessage());
        er.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(er,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException (Exception e){


        StudentErrorResponse er=new StudentErrorResponse();
        er.setStatus(HttpStatus.BAD_REQUEST.value());
        er.setMessage(e.getMessage());
        er.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(er,HttpStatus.BAD_REQUEST);
    }
}
