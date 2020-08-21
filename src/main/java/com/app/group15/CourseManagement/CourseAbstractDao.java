package com.app.group15.CourseManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;
import java.util.ArrayList;


public abstract class CourseAbstractDao<T> implements IDao {
    @Override
    public abstract Persistence get(int id) throws SQLException, AwsSecretsManagerException;

    @Override
    public abstract ArrayList<Course> getAll() throws SQLException, AwsSecretsManagerException;

    @Override
    public abstract int save(Persistence persistence) throws SQLException, AwsSecretsManagerException;

    @Override
    public abstract void update(Persistence persistence, int id) throws SQLException, AwsSecretsManagerException;

    @Override
    public abstract void delete(int id) throws SQLException, AwsSecretsManagerException;

}
