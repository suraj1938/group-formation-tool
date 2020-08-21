package com.app.group15.UserManagement.LoginManagement;

import java.sql.SQLException;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public interface ILoginService {
    boolean validateLogin(String bannerId, String password) throws SQLException, AwsSecretsManagerException;
}
