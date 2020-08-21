package com.app.group15.UserManagement;

import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public abstract class UserRoleAbstractDao<T> implements IDao {


    @Override
    public abstract Persistence get(int id) throws SQLException, AwsSecretsManagerException;

    public abstract int getRoleId(String role) throws SQLException,AwsSecretsManagerException;

    public abstract Set<String> getRolesByBannerId(String bannerId) throws SQLException,AwsSecretsManagerException;

    public abstract void addRole(int userId, String role) throws SQLException, AllowedRolesNotSetException,AwsSecretsManagerException;

    public abstract void updateRole(int userId, String role) throws AllowedRolesNotSetException, SQLException,AwsSecretsManagerException;

    @Override
    public abstract List<T> getAll() throws SQLException,AwsSecretsManagerException;

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
}
