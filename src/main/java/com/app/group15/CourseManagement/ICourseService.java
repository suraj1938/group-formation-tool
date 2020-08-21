package com.app.group15.CourseManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.Persistence;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;
import java.util.List;

public interface ICourseService {

    List<Course> getCoursesList() throws SQLException, AwsSecretsManagerException;

    Course getCourseDetails(int id) throws SQLException, AwsSecretsManagerException;

    User getCourseInstructor(int id) throws SQLException, AwsSecretsManagerException;

    List<User> getAllCourseInstructors(List<Course> courses);

    void addOrUpdateInstructor(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException;

    int addCourse(String courseName) throws SQLException , AwsSecretsManagerException;

    void deleteCourse(int courseId) throws SQLException, AwsSecretsManagerException;

    boolean isUserCourseAdmin(int courseId, int id) throws SQLException, AwsSecretsManagerException;

    List<Course> getStudentCourses(int id) throws SQLException, AwsSecretsManagerException;

    Course getStudentCourseAsTa(int id) throws AwsSecretsManagerException, SQLException;

    boolean validateCourseID(int courseId) throws SQLException, AwsSecretsManagerException;

    Persistence get(int courseId) throws SQLException, AwsSecretsManagerException;

}
