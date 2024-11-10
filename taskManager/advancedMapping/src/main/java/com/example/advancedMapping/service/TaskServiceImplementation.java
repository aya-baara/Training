package com.example.advancedMapping.service;

import com.example.advancedMapping.DAO.TaskRepository;
import com.example.advancedMapping.entity.Task;
import com.example.advancedMapping.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImplementation implements CRUDService<Task> {
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(int id) {
        Optional<Task>result=taskRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }


    @Override
    public void save(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void delete(int id) {
        if(findById(id)==null){
            throw new NotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
    }
}
