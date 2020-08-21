package com.app.group15.CourseManagement.Instructor;

import com.app.group15.CourseManagement.Course;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;
import java.util.List;

public abstract class CourseInstructorMapperAbstractDao<T> implements IDao {

    public abstract User getCourseInstructor(int id) throws SQLException, AwsSecretsManagerException;

    public abstract List<Course> getCourseByInstructor(int id) throws SQLException, AwsSecretsManagerException;

    public abstract List<Course> getCoursesByInstructor(int taId) throws AwsSecretsManagerException, SQLException;

    public abstract Course getCourseByTa(int id) throws AwsSecretsManagerException, SQLException;

    public abstract void deleteByCourseId(int courseId) throws SQLException, AwsSecretsManagerException;

    protected abstract boolean doesCourseIdExistInThisMapper(int courseId) throws SQLException, AwsSecretsManagerException;

    protected abstract void addInstructorForCourseWithTa(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException;

    protected abstract void addTaForCourseWithInstructor(int courseId, int taId) throws SQLException, AwsSecretsManagerException;

    public abstract void addInstructorToACourse(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException;

    public abstract void addTaToACourse(int courseId, int taId) throws SQLException, AwsSecretsManagerException;

    @Override
    public abstract List getAll() throws SQLException, AwsSecretsManagerException;

    @Override
    public int save(Persistence t) {
        return 0;
    }

    @Override
    public void update(Persistence t, int id) {
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public Persistence get(int id) {
        return null;
    }

    public abstract User getCourseTA(int userId) throws SQLException, AwsSecretsManagerException;


}
