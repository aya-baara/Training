package com.example.advancedMapping;

import com.example.advancedMapping.dao.AppDAO;
import com.example.advancedMapping.entity.Course;
import com.example.advancedMapping.entity.Instructor;
import com.example.advancedMapping.entity.InstructorDetail;
import com.example.advancedMapping.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AdvancedMappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedMappingApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (AppDAO appDAO){
		return runner->{
			createCourseAndReviews(appDAO);
			//findCourseAndReviewsByID(appDAO);

		};
	}

	private void findCourseAndReviewsByID(AppDAO appDAO) {
		int id=10;
		Course course=appDAO.findCourseAndReviewsById(id);

		System.out.println(course);
		System.out.println(course.getReviews());
	}

	private void createCourseAndReviews(AppDAO appDAO) {

		Course course=new Course("blablabla");

		course.addReview(new Review("love it"));
		course.addReview(new Review("amazing"));
		appDAO.save(course);
	}

	private void deleteCourse(AppDAO appDAO) {
		int id =10;
		appDAO.deleteCourse(id );
	}

	private void delereInstructorById(AppDAO appDAO) {
		int id=1;
		appDAO.deleteInstructorWithoutCourses(id);
	}

	private void updateCourse(AppDAO appDAO) {
		int id=11;
		Course course=appDAO.findCourseById(id);
		course.setTitle("blablablaaa");
		appDAO.updateCourse(course);
		System.out.println("Done updating the course");
	}

	private void updateInstructor(AppDAO appDAO) {
		int id=1;
		Instructor inst=appDAO.findInstructorById(1);
		inst.setEmail("mawaa123@gmail.com");
		appDAO.updateInstructor(inst);
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
		int id=1;
		Instructor temp = appDAO.findInstructorByIdJoinFetch(id);
		System.out.println(temp);
		System.out.println(temp.getCourses());

	}



	private void findCoursesForInstructor(AppDAO appDAO) {
		int id=1;
		Instructor tempIns=appDAO.findInstructorById(id);

		System.out.println("Temp instructor  " +tempIns );
		System.out.println("Finding courses");
		List<Course> tempCourses=appDAO.findCoursesByInstructorId(id);
		tempIns.setCourses(tempCourses);
		System.out.println("courses " +tempIns.getCourses());
	}

	private void createInstructorWithCourses(AppDAO appDAO) {
		Instructor instructor=new Instructor("marwa","naser","marwaNaser@gmail.com");

		InstructorDetail tempInstructorDetail=new InstructorDetail("www.","coding");
		instructor.setInstructorDetail(tempInstructorDetail);

		Course course=new Course("lalala");
		Course course2=new Course("course 2");

		instructor.add(course);
		instructor.add(course2);
		System.out.println(instructor);
		appDAO.save(instructor);
		System.out.println("Done Saving");


	}

	private void deleteInsructorDetailById(AppDAO appDAO) {
		int id=3;
		appDAO.deleteInstructorDetailById(id);
		System.out.println("Done deleting");
	}

	private void findInstructorDetailById(AppDAO appDAO) {
		int id=2;
		InstructorDetail temp =appDAO.findInstructorDetailById(id);
		System.out.println("Detail " + temp);
		System.out.println("Instructor = "+temp.getInstructor());
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
