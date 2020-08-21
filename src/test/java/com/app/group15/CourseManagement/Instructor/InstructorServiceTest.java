package com.app.group15.CourseManagement.Instructor;

import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.CourseManagement.Course;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InstructorServiceTest {
    
    private IInstructorService instructorService=ServiceConfigForTest.getInstance().getInstructorService();

    @Test
    public void getCourseOfInstructorTest() throws AwsSecretsManagerException, SQLException {
        assertTrue(instructorService.getCourseOfInstructor(1).size()>0);
    }

    @Test
    public void getCourseTATest() throws SQLException, AwsSecretsManagerException {
        assertTrue(instructorService.getCourseTA(1).getId()==1);
    }

    @Test
    public void getAllCourseTATest() throws SQLException, AwsSecretsManagerException {
        List<Course> courseList=new ArrayList();
        Course course=new Course();
        courseList.add(course);
        assertTrue(instructorService.getAllCourseTA(courseList).size()>0);

    }
    
    @Test
    public void validateUserToAddAsTaTest() throws SQLException, AwsSecretsManagerException {
    	
    	assertEquals(instructorService.validateUserToAddAsTa(new User(), 2),true);
    }
    
    @Test
    public void checkInstructorPermissionTest() throws AwsSecretsManagerException, SQLException {
    	assertEquals(instructorService.checkInstructorPermission(1, 1),true);
    }
    
    @Test
    public void validateUserToAddAsStudentTest() throws SQLException, AwsSecretsManagerException {
    	assertEquals(instructorService.validateUserToAddAsTa(new User(), 2),true);
    }
}
