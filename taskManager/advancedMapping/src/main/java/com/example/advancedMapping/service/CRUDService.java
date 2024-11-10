package com.example.advancedMapping.service;

import com.example.advancedMapping.entity.User;

import java.util.List;

public interface CRUDService <T> {
    public List <T> findAll();
    public T findById(int id);
    public void save(T t);
    public void delete(int id);

}
