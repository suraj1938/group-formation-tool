package com.app.group15.UserManagement;

import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class UserAbstractDao<T> implements IDao {
    @Override
    public void delete(int id) {
    }


    @Override
    public int save(Persistence user) {
        return 0;

    }

    @Override
    public abstract Persistence get(int id) throws SQLException, AwsSecretsManagerException;

    public abstract User getUserByBannerId(String bannerId) throws SQLException,AwsSecretsManagerException;

    @Override
    public abstract ArrayList<User> getAll() throws SQLException,AwsSecretsManagerException;

    public abstract String getUserPassword(int userId) throws SQLException,AwsSecretsManagerException;

    public abstract int saveUser(User user, String role) throws AllowedRolesNotSetException, AwsSecretsManagerException, SQLException;

    @Override
    public abstract void update(Persistence persistence, int id) throws SQLException,AwsSecretsManagerException;

    public abstract void updateUserRole(int userId, String role) throws SQLException,AwsSecretsManagerException;

    public abstract User getUserByEmailId(String emailId) throws SQLException,AwsSecretsManagerException;
}
