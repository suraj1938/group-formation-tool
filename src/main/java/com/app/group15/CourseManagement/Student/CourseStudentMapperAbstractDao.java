package com.app.group15.CourseManagement.Student;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class CourseStudentMapperAbstractDao<T> implements IDao {


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

    public abstract int addStudentToACourse(int courseId, int studentId) throws SQLException, AwsSecretsManagerException;

    public abstract void deletByCourseId(int courseId) throws SQLException, AwsSecretsManagerException;

    @Override
    public abstract List<T> getAll() throws SQLException, AwsSecretsManagerException;

    public abstract ArrayList<Integer> getCourseIdsOfAStudent(int studentId) throws SQLException, AwsSecretsManagerException;
}
