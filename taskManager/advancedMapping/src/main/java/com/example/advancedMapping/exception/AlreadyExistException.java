package com.example.advancedMapping.exception;

public class AlreadyExistException extends RuntimeException  {

    public AlreadyExistException(){
        super("This username is already registered ");
    }

}