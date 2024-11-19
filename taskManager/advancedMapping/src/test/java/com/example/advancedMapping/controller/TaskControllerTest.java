package com.example.advancedMapping.controller;

import com.example.advancedMapping.DAO.TaskRepository;
import com.example.advancedMapping.entity.Task;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.security.JWTSecurity.JwtUtil;
import com.example.advancedMapping.security.UserDetailsServiceImpl;
import com.example.advancedMapping.service.TaskServiceImplementation;
import com.example.advancedMapping.service.UserServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)

public class TaskControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    TaskServiceImplementation taskServiceImplementation;

    @MockBean
    UserServiceImplementation userServiceImplementation;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    JwtUtil jwtUtil;
    private final User user=new User(1,"aya","Aya","Jamal","ayabaara@gmail.com","123","user");

    private final String taskName = "Complete Project";
    private final String taskPriority = "High";
    private final LocalDate taskDeadline = LocalDate.of(2024, 12, 31);  // Example: December 31, 2024
    private final String taskDescription = "Complete the final project for the course";
    private final LocalDate taskStartDate = LocalDate.of(2024, 11, 18);  // Example: November 18, 2024
    private final LocalDate taskEndDate = LocalDate.of(2024, 12, 15);  // Example: December 15, 2024

    // Create the Task object
    private final Task task = new Task(taskName, taskPriority, taskDeadline, taskDescription, taskStartDate, taskEndDate);
    private final Task task2 = new Task(taskName, taskPriority, taskDeadline, taskDescription, taskStartDate, taskEndDate);


    @WithMockUser
    @Test
    void returnAllTasks() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        mockMvc.perform( MockMvcRequestBuilders
                .get("/tasks")
                .header("Authorization", token) // Add JWT token here
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void returnAllTasksBtPage() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tasksPage")
                        .param("page", "1")
                        .param("sortBy", "name")
                        .param("sortDirection", "asc")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) ;

    }



    @Test
    void returnTaskById() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        mockMvc.perform( MockMvcRequestBuilders
                .get("/tasks/0")
                .header("Authorization", token) // Add JWT token here
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


    //// add task
    @WithMockUser
    @Test
    void addTask_AsAdmin() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        // Prepare JSON input for admin
        String adminTaskJson = """
        {
            "name": "Admin Task",
            "priority": "High",
            "deadLine": "2024-11-30",
            "description": "Task Description",
            "startDate": "2024-11-20",
            "endDate": "2024-11-29",
            "role": "admin",
            "userId": 123
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/tasks")
                        .header("Authorization", token) // Add JWT token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminTaskJson))
                .andExpect(status().isOk());

    }
    @WithMockUser
    @Test
    void addTaskAsUser() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        // Prepare JSON input for non-admin
        String nonAdminTaskJson = """
        {
            "name": "User Task",
            "priority": "Low",
            "deadLine": "2024-12-01",
            "description": "Non-Admin Task Description",
            "startDate": "2024-11-21",
            "endDate": "2024-12-05",
            "role": "user"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/tasks")
                        .header("Authorization", token) // Add JWT token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nonAdminTaskJson))
                .andExpect(status().isOk());

    }


    @WithMockUser
    @Test
    void editTask() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        String taskJson = objectMapper.writeValueAsString(this.task);
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/tasks")
                .header("Authorization", token) // Add JWT token here
                .accept(MediaType.APPLICATION_JSON).content(taskJson)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void deleteTask() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbSIsImV4cCI6MTczMTk2MTYxNCwiaWF0IjoxNzMxOTI1NjE0fQ.sdObS87lXde6nYZkD7FHl6MC-WxokQrVFLddvNRdcRw";

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/tasks")
                .header("Authorization", token) // Add JWT token here
                .param("id", "0") // Add request parameter here
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }







}
