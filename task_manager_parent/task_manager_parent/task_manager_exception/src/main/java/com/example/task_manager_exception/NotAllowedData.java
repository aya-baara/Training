package com.example.task_manager_exception;

public class NotAllowedData  extends  RuntimeException{

    public NotAllowedData(String message){
        super(message);
    }
}
