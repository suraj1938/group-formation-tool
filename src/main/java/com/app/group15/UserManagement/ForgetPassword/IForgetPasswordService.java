package com.app.group15.UserManagement.ForgetPassword;

import javax.servlet.http.HttpServletRequest;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

public interface IForgetPasswordService {
    void generateToken(String bannerId, HttpServletRequest request) throws SQLException, UnsupportedEncodingException, AwsSecretsManagerException;

    boolean verifyToken(String token) throws SQLException, ParseException, AwsSecretsManagerException;

    Map<String, String> getUserFromToken(String token) throws SQLException, AwsSecretsManagerException;

    boolean updateUserPassword(int id, String newPassword) throws SQLException, AwsSecretsManagerException;

    boolean deleteForgotPasswordToken(int id) throws SQLException, AwsSecretsManagerException;
}
