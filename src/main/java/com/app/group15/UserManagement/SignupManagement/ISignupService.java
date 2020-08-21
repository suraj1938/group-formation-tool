package com.app.group15.UserManagement.SignupManagement;

import java.sql.SQLException;

import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;

public interface ISignupService {
    boolean checkUserExists(String bannerId) throws SQLException, AwsSecretsManagerException;

    int createUser(User user, String role) throws AllowedRolesNotSetException, AwsSecretsManagerException, SQLException;
}
