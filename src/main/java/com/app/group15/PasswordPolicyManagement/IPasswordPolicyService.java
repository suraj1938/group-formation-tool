package com.app.group15.PasswordPolicyManagement;

import java.sql.SQLException;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public interface IPasswordPolicyService {

    PasswordPolicyValidationResult validatePassword(String password, int userId) throws SQLException, AwsSecretsManagerException;


}
