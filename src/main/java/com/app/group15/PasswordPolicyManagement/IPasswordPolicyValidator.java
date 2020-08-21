package com.app.group15.PasswordPolicyManagement;

import java.sql.SQLException;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public interface IPasswordPolicyValidator {

    boolean isPasswordValid(String password) throws SQLException, AwsSecretsManagerException;


}
