package com.example.advancedMapping;

import com.example.advancedMapping.dao.AppDAO;
import com.example.advancedMapping.entity.Instructor;
import com.example.advancedMapping.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdvancedMappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedMappingApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (AppDAO appDAO){
		return runner->{
			//createInstructor(appDAO);
			//findInstructorr(appDAO);
			delereInstructor(appDAO);
		};
	}

	private void delereInstructor(AppDAO appDAO) {
		int id=1;
		appDAO.deleteInstructor(id);
		System.out.println("Done deleting");
	}

	private void findInstructorr(AppDAO appDAO) {
		int id=1;
		Instructor temp= appDAO.findInstructorById(id);
		System.out.println("temp Instructor " +temp);
		System.out.println("temp Instructor details " +temp.getInstructorDetail());

	}

	private void createInstructor(AppDAO appDAO) {

		Instructor temp=new Instructor("aya","baara","ayabaara@gmail.com");
		InstructorDetail tempInstructorDetail=new InstructorDetail("www.","coding");
		temp.setInstructorDetail(tempInstructorDetail);
		System.out.println(temp);
		appDAO.save(temp);
		System.out.println("Done Saving");
	}


}
