package com.app.group15.CourseManagement;

import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.CourseManagement.Instructor.CourseInstructorMapper;
import com.app.group15.CourseManagement.Instructor.CourseInstructorMapperDaoMock;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CourseServiceTest {

    
    private ICourseService courseService=ServiceConfigForTest.getInstance().getCourseService();

    @Test
    public void getCoursesListTest() throws SQLException, AwsSecretsManagerException {
    	List courseList=courseService.getCoursesList();
        assertTrue(courseList.size() >0);
  
    }

    @Test
    public void getCourseDetailsTest() throws SQLException, AwsSecretsManagerException {
        
        assertEquals(courseService.getCourseDetails(1).getId(), 1);
    }

    @Test
    public void getCourseInstructorTest() throws SQLException, AwsSecretsManagerException {
        assertEquals(courseService.getCourseInstructor(1).getId(), 1);
    }

    @Test
    public void getAllCourseInstructorsTest() {
        ArrayList<Course> courses = new ArrayList<>();
        Course c1 = new Course();
        c1.setName("CSCI5409");
        c1.setId(1);
        courses.add(c1);
        
       assertTrue(courseService.getAllCourseInstructors(courses).size()>0);
    }




    @Test
    public void addCourseTest() throws SQLException, AwsSecretsManagerException {
      
        assertEquals(courseService.addCourse("C1"), 1);
        
    }

    
    @Test
    public void getStudentCoursesTest() throws SQLException, AwsSecretsManagerException {
    	assertTrue(courseService.getStudentCourses(1).size()>0);
    }
    
    @Test
    public void getStudentCourseAsTaTest() throws AwsSecretsManagerException, SQLException {
    	assertEquals(courseService.getStudentCourseAsTa(1).getId(),1);
    }
    @Test
    public void validateCourseIDTest() throws SQLException, AwsSecretsManagerException {
    	assertEquals(courseService.validateCourseID(1),true);
    	
    }

}
