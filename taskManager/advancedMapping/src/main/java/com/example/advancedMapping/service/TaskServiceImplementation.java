package com.example.advancedMapping.service;

import com.example.advancedMapping.DAO.TaskRepository;
import com.example.advancedMapping.DAO.UserRepository;
import com.example.advancedMapping.entity.Task;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.exception.NotAllowedData;
import com.example.advancedMapping.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImplementation  {
    private TaskRepository taskRepository;
    private UserServiceImplementation userServiceImplementation;



    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository,UserServiceImplementation userServiceImplementation){
        this.taskRepository=taskRepository;
        this.userServiceImplementation=userServiceImplementation;
    }
    public List<Task> findAll() {
        return taskRepository.findAll();
    }



    public Task getTask(int id) throws AccessDeniedException {
        Task result=taskRepository.findById(id);

        if (result!=null) {
            User requestingUser= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(requestingUser.getId()!=result.getUser().getId()){
                throw new AccessDeniedException("you don't have access to this task");
            }
            return result;
        }
        throw new NotFoundException("Task not found");

    }



    public Task addTask(Task task)  {

        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        checkTimeValidation(task,user);
        task.setUser(user);
        taskRepository.save(task);

        return task;
    }

    public Task addTaskByAdmin(Task task,int id) {
       User user=userServiceImplementation.findById(id);

        checkTimeValidation(task,user);
        task.setUser(user);
        taskRepository.save(task);

        return task;
    }

    public void delete(int id) throws AccessDeniedException {
        Task result=taskRepository.findById(id);
        System.out.println(result);
        if (result!=null) {
            User requestingUser= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(requestingUser.getId()!=result.getUser().getId()){
                throw new AccessDeniedException("you don't have access to this task");
            }
            taskRepository.delete(result);
        }
        else{throw new NotFoundException("Task not found");}

    }

    public Task updateTask(Task task) throws AccessDeniedException {
        User requestingUser= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task temp=taskRepository.findById(task.getId());
        if(temp.getUser().getId()!=requestingUser.getId()){throw new AccessDeniedException("You don't have access to this task");}
        task.setUser(requestingUser);
        checkTimeValidation(task,requestingUser);
        taskRepository.save(task);
        return task;

    }

    public Page<Task> getAllTasks(Optional<Integer> page, Optional <String> sortDirection , Optional<String> sortBy ){
        User requestingUser= (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return taskRepository.findAllByUser_Id(requestingUser.getId(), PageRequest.of(page.orElse(0), 2,
                Sort.Direction.fromString(sortDirection.orElse("asc")),sortBy.orElse("id")));
    }

    public void checkTimeValidation(Task task,User user) {


        List<Task> tasks = taskRepository.findAllByUser_IdAndEndDateIsAfterAndStartDateBefore(
                user.getId(), task.getStartDate(), task.getEndDate());

        int count = tasks.size();
        System.out.println("<<<>>>> user id " +user.getId());
        System.out.println("<<<>>>> count " +count);
        if (count > 1) {
            throw new NotAllowedData("invalid Date");
        } else if (count == 1) {

            if (tasks.get(0).getId() != task.getId()) {
                throw new NotAllowedData("invalid time");
            }
            // If ID match, it’s the same task being edited, so it's allowed
        }


    }

}
