package com.app.group15.UserManagement.SessionManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public interface ISessionService {
    boolean isUserSignedIn(HttpServletRequest request);

    User getSessionUser(HttpServletRequest request) throws SQLException, AwsSecretsManagerException;

    void destroySession(HttpServletRequest request);

    void setSession(HttpServletRequest request, String name, String value);

    String getUserHome(HttpServletRequest request) throws SQLException, AwsSecretsManagerException;

}
