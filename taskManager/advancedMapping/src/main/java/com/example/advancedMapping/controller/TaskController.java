package com.example.advancedMapping.controller;


import com.example.advancedMapping.entity.Task;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.service.TaskServiceImplementation;
import com.example.advancedMapping.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
//@RequestMapping("/api")
public class TaskController {

    private TaskServiceImplementation taskServiceImplementation;
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    public TaskController(TaskServiceImplementation taskServiceImplementation,
                          UserServiceImplementation userServiceImplementation){
        this.taskServiceImplementation=taskServiceImplementation;
        this.userServiceImplementation=userServiceImplementation;
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() throws AccessDeniedException {
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Role = " +user.getRole());
        if(!user.getRole().equalsIgnoreCase("admin")){
            throw new AccessDeniedException("Only admins can access this");
        }
        return taskServiceImplementation.findAll();
    }

    @GetMapping("/tasksPage")
    public Page<Task> returnAllTasks(@RequestParam Optional<Integer> page ,
                                     @RequestParam Optional <String> sortBy ,
                                     @RequestParam Optional<String> sortDirection) {

        return taskServiceImplementation.getAllTasks(page ,sortDirection,sortBy);
    }

    @GetMapping("/tasks/{id}")
    public Task getUserById(@PathVariable int id) throws AccessDeniedException {
        return taskServiceImplementation.getTask(id);
    }

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Map<String, Object> taskData) throws AccessDeniedException {
        Task task = new Task(((String) taskData.get("name")),((String) taskData.get("priority")),
                (LocalDate.parse((String) taskData.get("deadLine"))), ((String) taskData.get("description")),
                (LocalDate.parse((String) taskData.get("startDate"))),(LocalDate.parse((String) taskData.get("endDate"))));

        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Role = " +user.getRole());

        task.setId(0);  // Ensure ID is 0 to create a new task

        if(!user.getRole().equalsIgnoreCase("admin")){
            taskServiceImplementation.addTask(task);
        }
        else{
            Integer userId = (Integer) taskData.get("userId");
            System.out.println("User Id>>> " +userId);
            taskServiceImplementation.addTaskByAdmin(task,userId);

        }
        return task;
    }
    @PatchMapping("/tasks")
    public Task updateTask(@RequestBody Task task ) throws AccessDeniedException {

        return taskServiceImplementation.updateTask(task) ;
    }

    @DeleteMapping("/tasks")
    public String deleteTask(@RequestParam int id) throws AccessDeniedException {

        taskServiceImplementation.delete(id);
        return "deleted successfully";
    }
}
