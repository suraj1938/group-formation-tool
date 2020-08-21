package com.app.group15.UserManagement;

import java.sql.SQLException;
import java.util.List;

import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public interface IUserService {

    List<User> getAllUsers() throws SQLException, AwsSecretsManagerException;

    void updateUserRole(int userId, String role) throws AllowedRolesNotSetException, SQLException,AwsSecretsManagerException;

    boolean validateBannerID(String bannerId) throws SQLException,AwsSecretsManagerException;
    public User getUser(int userId) throws SQLException,AwsSecretsManagerException;
}
