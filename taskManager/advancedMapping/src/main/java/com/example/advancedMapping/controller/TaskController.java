package com.example.advancedMapping.controller;


import com.example.advancedMapping.entity.Task;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.service.TaskServiceImplementation;
import com.example.advancedMapping.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public List<Task> getAllTasks(){
        return taskServiceImplementation.findAll();
    }
    @GetMapping("/tasks/{id}")
    public Task getUserById(@PathVariable int id){
        return taskServiceImplementation.findById(id);
    }
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Map<String, Object> taskData){
        Task task = new Task(((String) taskData.get("name")),((String) taskData.get("priority")),
                (LocalDate.parse((String) taskData.get("deadLine"))), ((String) taskData.get("description")));

        // Retrieve User by userId and set it
        Integer userId = (Integer) taskData.get("userId");
        User user = userServiceImplementation.findById(userId);
        task.setUser(user);

        task.setId(0);  // Ensure ID is 0 to create a new task
        taskServiceImplementation.save(task);
        return task;
    }
    @PutMapping("/tasks/{userId}")
    public Task updateTask(@RequestBody Task task , @PathVariable int userId) {
        User user = userServiceImplementation.findById(userId);
        task.setUser(user);
        taskServiceImplementation.save(task);
        return task;
    }

    @DeleteMapping("/tasks")
    public String deleteTask(@RequestParam int id){

        taskServiceImplementation.delete(id);
        return "deleted successfully";
    }
}
