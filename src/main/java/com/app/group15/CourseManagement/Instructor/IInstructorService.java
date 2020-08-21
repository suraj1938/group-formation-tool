package com.app.group15.CourseManagement.Instructor;

import com.app.group15.CourseManagement.Course;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

public interface IInstructorService {
    List<Course> getCourseOfInstructor(int instructorId) throws AwsSecretsManagerException, SQLException;

    User getCourseTA(int id) throws SQLException, AwsSecretsManagerException;

    List<User> getAllCourseTA(List<Course> courseEntities) throws SQLException, AwsSecretsManagerException;

    boolean validateUserToAddAsTa(User user, int courseId) throws SQLException, AwsSecretsManagerException;

    void addOrUpdateStudentRole(User user, String role) throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException;

    int addStudentsFromCSV(MultipartFile csvFile, int courseId) throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException;

    boolean checkInstructorPermission(int instructorId, int courseId) throws AwsSecretsManagerException, SQLException;

}
