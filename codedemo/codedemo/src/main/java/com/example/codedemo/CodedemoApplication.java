package com.example.codedemo;

import com.example.codedemo.dao.StudentDAO;
import com.example.codedemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CodedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodedemoApplication.class, args);
	}

/*	public CommandLineRunner commandLineRunner(StudentDAO studentDAO){

        return runner->{
			createUser(studentDAO );
			//readStudent(studentDAO);
			//queryForStudent(studentDAO);
			//queryByLastName(studentDAO);
			//updateStudent(studentDAO);
			//deleteStudent(studentDAO);
    };*/
}

	/*private void deleteStudent(StudentDAO studentDAO) {
		int theId=3;
		studentDAO.delete(theId);

	}

	private void updateStudent(StudentDAO studentDAO) {
		Student temp=studentDAO.findById(1);
		temp.setFirstName("scoopy doby");
		studentDAO.update(temp);
		System.out.println("updated version of student "+temp);
	}

	private void queryByLastName(StudentDAO studentDAO) {
		List<Student> theStudents=studentDAO.findByLastName("Baara");
		for(Student temp:theStudents){
			System.out.println(temp);

		}

	}


	private void queryForStudent(StudentDAO studentDAO) {

		List<Student>theStudents=studentDAO.findALL();
		for(Student temp:theStudents){
			System.out.println(temp);

		}
	}


	private void readStudent(StudentDAO studentDAO) {
		    Student temp=new Student("Daffy","Duck","dfdf.com");
			studentDAO.save(temp);

			int theId=temp.getId();

			Student myStudent=studentDAO.findById(theId);
		System.out.println("found this student    "+myStudent);


	}

	private void createUser(StudentDAO studentDAO) {
		// create the student
		System.out.println("Creating new Student .....");
		Student stu=new Student("sama","samer","samaHasiba@gmil.com");

		// save the student
		System.out.println("Saving the Student");
		studentDAO.save(stu);

		// display id of the saved student
		System.out.println("Saved student Id "+ stu.getId());

	}*/
	//}
