package com.example.advancedMapping;


import com.example.advancedMapping.DAO.UserRepository;
import com.example.advancedMapping.entity.Task;
import com.example.advancedMapping.entity.User;
import com.example.advancedMapping.security.UserDetailsServiceImpl;
import com.example.advancedMapping.service.TaskServiceImplementation;
import com.example.advancedMapping.service.UserServiceImplementation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class AdvancedMappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedMappingApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (TaskServiceImplementation taskServiceImplementation,
												UserServiceImplementation userServiceImplementation,
												UserDetailsServiceImpl userDetailsService,
												UserRepository userRepository){
		return runner->{

//			System.out.println("From repository =========>>>>>" +userRepository.findByUsername("smsm"));
//			System.out.println("From user details ======>"+userDetailsService.loadUserByUsername("smsm"));

			//addUser(userServiceImplementation);
			//updateUser(userServiceImplementation);
			//getAllUsers(userServiceImplementation);
			//deleteUser(userServiceImplementation);

			//addTask(taskServiceImplementation,userServiceImplementation);
			//getTask(taskServiceImplementation);
			//getAllTasks(taskServiceImplementation);

		};
	}

	private void deleteUser(UserServiceImplementation userServiceImplementation) {
		int id=9;
		userServiceImplementation.delete(id);
	}

	private void getAllTasks(TaskServiceImplementation taskServiceImplementation) {
		List<Task> tasks=taskServiceImplementation.findAll();
		for(Task temp:tasks){
			System.out.println(temp);
			System.out.println(temp.getUser());
		}

	}

	private void getTask(TaskServiceImplementation taskServiceImplementation) {
		Task task=taskServiceImplementation.findById(1);
		System.out.println(task);
		System.out.println(task.getUser());
	}


	private void addTask(TaskServiceImplementation taskServiceImplementation, UserServiceImplementation userServiceImplementation) {
		int id=11;
		User user=userServiceImplementation.findById(id);
		LocalDate deadline = LocalDate.of(2024, 12, 3);
		Task task=new Task("blablabla","high",deadline,"blablabla");
		task.setUser(user);
		System.out.println("task = "+task + " user = "+task.getUser());
		taskServiceImplementation.save(task);
	}

	private void getAllUsers(UserServiceImplementation userServiceImplementation) {
		List<User>users=userServiceImplementation.findAll();
		for(User temp:users){
			System.out.println(temp);
		}
	}

	private void updateUser(UserServiceImplementation userServiceImplementation) {
		int id=8;
		User user=userServiceImplementation.findById(id);
		user.setEmail("baara@gmail.com");
		userServiceImplementation.save(user);
	}

	private void addUser(UserServiceImplementation userServiceImplementation) {
		User user=new User("smsm","smasm","smsm@gmail");
		userServiceImplementation.save(user);
	}




}
