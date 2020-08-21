package com.app.group15.CourseManagement.Instructor;

import com.app.group15.Config.ServiceConfig;
import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.CourseManagement.Course;
import com.app.group15.CourseManagement.CourseDaoMock;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;
import com.app.group15.UserManagement.UserDaoMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class AssignTAServiceTest {

    private IAssignTAService assignTaService=ServiceConfigForTest.getInstance().getAssignTaService();

    @Test
    void performTAUpdateTest() throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException {
        assertEquals(assignTaService.performTAUpdate("B00854472", 2),true);
    }

   
}