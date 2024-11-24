package com.example.task_manager_exception;

public class AlreadyExistException extends RuntimeException  {

    public AlreadyExistException(){
        super("This username is already registered ");
    }

}