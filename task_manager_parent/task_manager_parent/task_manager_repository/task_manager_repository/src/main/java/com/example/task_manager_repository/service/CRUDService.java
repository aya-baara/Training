package com.example.task_manager_repository.service;


import java.util.List;

public interface CRUDService <T> {
    public List <T> findAll();
    public T findById(int id);
    public void save(T t);
    public void delete(int id);

}
