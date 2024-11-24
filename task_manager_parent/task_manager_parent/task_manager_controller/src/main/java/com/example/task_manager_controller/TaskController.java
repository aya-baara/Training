package com.example.task_manager_controller;



import com.example.task_manager_repository.entity.Task;
import com.example.task_manager_repository.service.TaskServiceImplementation;
import com.example.task_manager_repository.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
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
        // Extract task details from the request body
        Task task = new Task(
                (String) taskData.get("name"),
                (String) taskData.get("priority"),
                LocalDate.parse((String) taskData.get("deadLine")),
                (String) taskData.get("description"),
                LocalDate.parse((String) taskData.get("startDate")),
                LocalDate.parse((String) taskData.get("endDate"))
        );

        // Extract role from the request body
        String role = (String) taskData.get("role");
        task.setId(0); // Ensure ID is 0 to create a new task

        // Check if the role is admin
        if ("admin".equalsIgnoreCase(role)) {
            Integer userId = (Integer) taskData.get("userId");
            if (userId == null) {
                throw new IllegalArgumentException("User ID is required for admin role");
            }
            System.out.println("Admin User ID >>> " + userId);
            taskServiceImplementation.addTaskByAdmin(task, userId);
        } else {
            // Default behavior for non-admin roles
            taskServiceImplementation.addTask(task);
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
