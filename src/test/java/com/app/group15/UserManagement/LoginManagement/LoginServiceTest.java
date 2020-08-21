package com.app.group15.UserManagement.LoginManagement;

import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LoginServiceTest {

    private ILoginService loginService = ServiceConfigForTest.getInstance().getLoginService();

    @Test
    public void validateLoginTest() throws SQLException, AwsSecretsManagerException {

        boolean passwordCorrect = loginService.validateLogin("B00843468", "passwordTest");
        assertEquals(passwordCorrect, true);
        boolean passwordInCorrect = loginService.validateLogin("B00843468", "password");
        assertNotEquals(passwordInCorrect, true);
    }
}
