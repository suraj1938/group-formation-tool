package com.app.group15.CourseManagement.Instructor;

import java.sql.SQLException;

import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public interface IAssignTAService {
    boolean performTAUpdate(String bannerId, int courseId) throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException;

}
