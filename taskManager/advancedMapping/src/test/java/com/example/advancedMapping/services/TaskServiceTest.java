package com.example.advancedMapping.services;

import com.example.advancedMapping.DAO.TaskRepository;
import com.example.advancedMapping.DAO.UserRepository;
import com.example.advancedMapping.entity.Task;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.exception.NotAllowedData;
import com.example.advancedMapping.exception.NotFoundException;
import com.example.advancedMapping.service.TaskServiceImplementation;
import com.example.advancedMapping.service.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
    public class TaskServiceTest {
        @Mock
        private TaskRepository taskRepository;
        @Mock
        private UserRepository userRepository;
        @InjectMocks
        private TaskServiceImplementation taskServiceImplementation;

    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

        private final User user=new User(1,"aya","Aya","Jamal","ayabaara@gmail.com","123");
        private final User admin=new User(2,"lana","Lana","Jamal","lana@gmail.com","123","admin");
        private final User user2=new User(3,"sama","Sama","sami","sama@gmail.com","123");        // Define the task parameters


        private final String taskName = "Complete Project";
        private final String taskPriority = "High";
        private final LocalDate taskDeadline = LocalDate.of(2024, 12, 31);  // Example: December 31, 2024
        private final String taskDescription = "Complete the final project for the course";
        private final LocalDate taskStartDate = LocalDate.of(2024, 11, 18);  // Example: November 18, 2024
        private final LocalDate taskEndDate = LocalDate.of(2024, 12, 15);  // Example: December 15, 2024

        // Create the Task object
        private final Task task = new Task(taskName, taskPriority, taskDeadline, taskDescription, taskStartDate, taskEndDate);
    private final Task task2 = new Task(taskName, taskPriority, taskDeadline, taskDescription, taskStartDate, taskEndDate);




        @Test
        void getTaskPass() throws AccessDeniedException {
            this.task.setUser(this.user);
            this.user.addTask(this.task);

            when(taskRepository.findById(this.task.getId())).thenReturn(this.task);



            SecurityContext context = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);
            when(context.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(context);
            when(SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal()).thenReturn(this.user);

            assertEquals(this.task, taskServiceImplementation.getTask(this.task.getId()));
        }

    @Test
    void getTaskFailNotFoundException() throws Exception {

        when(taskRepository.findById(this.task.getId())).thenReturn(null);

        // Assert
        assertThrows(NotFoundException.class,
                ()-> taskServiceImplementation.getTask(this.task.getId()));
    }

    @Test
    void getTaskFailAccessDenied() throws AccessDeniedException {
        this.task.setUser(this.user);
        this.user.addTask(this.task);

        when(taskRepository.findById(this.task.getId())).thenReturn(this.task);



        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user2);

        // Assert
        assertThrows(AccessDeniedException.class,
                ()-> taskServiceImplementation.getTask(this.task.getId()));
    }

    @Test
    void addTaskPass() throws Exception {

        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);


        when(taskRepository.findAllByUser_IdAndEndDateIsAfterAndStartDateBefore
                (this.user.getId(),this.task.getStartDate(),this.task.getEndDate())).thenReturn(new ArrayList<>());

        when(taskRepository.save(task)).thenReturn(task);
        assertEquals(task, taskServiceImplementation.addTask(task));

    }

    @Test
    void addTaskFail() throws Exception {

        ArrayList<Task>tasks=new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);

        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);

        when(taskRepository.findAllByUser_IdAndEndDateIsAfterAndStartDateBefore
                (this.user.getId(),this.task.getStartDate(),this.task.getEndDate())).thenReturn(tasks);

        // Assert
        assertThrows(NotAllowedData.class,
                ()-> taskServiceImplementation.addTask(task));

    }



    @Test
    void deleteTaskPass() throws Exception {
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);
        this.task.setUser(this.user);
        this.user.addTask(this.task);

        System.out.println(this.task.getId());
        when(taskRepository.findById(task.getId())).thenReturn(this.task);

        taskServiceImplementation.delete(this.task.getId());

        verify(taskRepository,times(1)).delete(this.task);
        }

    @Test
    void deleteTaskFailAccessDenied() throws Exception {
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);

        this.task.setUser(this.user2);
        this.user2.addTask(this.task);

        System.out.println(this.task.getId());
        when(taskRepository.findById(task.getId())).thenReturn(this.task);


        // Assert
        assertThrows(AccessDeniedException.class,
                ()-> taskServiceImplementation.delete(this.task.getId()));
    }

    @Test
    void deleteTaskFailNotFound() throws Exception {

        this.task.setUser(this.user);
        this.user.addTask(this.task);


        when(taskRepository.findById(5)).thenReturn(null);
        // Assert
        assertThrows(NotFoundException.class,
                ()-> taskServiceImplementation.delete(5));
    }


    @Test
    void updateTaskPass() throws Exception {
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);
        this.task.setUser(this.user);
        this.user.addTask(this.task);

        System.out.println(this.task.getId());
        when(taskRepository.findById(task.getId())).thenReturn(this.task);


        assertEquals(task,   taskServiceImplementation.updateTask(this.task));
    }

    @Test
    void updateTaskFailAccessDenied() throws Exception {
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user2);
        this.task.setUser(this.user);
        this.user.addTask(this.task);

        System.out.println(this.task.getId());
        when(taskRepository.findById(task.getId())).thenReturn(this.task);


        // Assert
        assertThrows(AccessDeniedException.class,
                ()->  taskServiceImplementation.updateTask(this.task));
    }


    @Test
    void getAllTasks() throws Exception {
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(this.user);
        List<Task> tasks = new ArrayList<>();
        user.addTask(task);
        task.setUser(user);
        tasks.add(task);

        user.addTask(task2);
        task2.setUser(user);
        tasks.add(task2);

        Page<Task> tasksPage = new PageImpl<>(tasks, PageRequest.of(0, 3), tasks.size());

        Optional<Integer> page = Optional.of(1);
        Optional<String> sortBy = Optional.of("id");
        Optional<String> sortDirection = Optional.of("asc");
        when(taskRepository.findAllByUser_Id(this.user.getId(),
                PageRequest.of(page.orElse(0), 2,
                        Sort.Direction.fromString(sortDirection.orElse("asc")),
                        sortBy.orElse("id")))).thenReturn(tasksPage);
        assertEquals(tasksPage, taskServiceImplementation.getAllTasks(page, sortDirection, sortBy));



        }

}






