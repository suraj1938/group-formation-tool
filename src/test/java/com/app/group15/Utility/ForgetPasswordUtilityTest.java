package com.app.group15.Utility;

import com.app.group15.UserManagement.ForgetPassword.ForgetPasswordUtility;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ForgetPasswordUtilityTest {

    @Test
    public void generateForgotPasswordTokenTest() {
        String token = ForgetPasswordUtility.generateForgotPasswordToken();
        assertNotNull(token);
        assertNotEquals(token, "");
    }

    @Test
    public void getTimeDifferenceTest() {
        try {
            long timeDifference = ForgetPasswordUtility.getTimeDifference(new Date());
            assertNotNull(timeDifference);
        } catch (ParseException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
