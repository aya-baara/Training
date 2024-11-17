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



}
