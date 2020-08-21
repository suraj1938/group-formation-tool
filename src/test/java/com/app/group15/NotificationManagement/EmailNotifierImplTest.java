package com.app.group15.NotificationManagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class EmailNotifierImplTest {

    

    @Test
    void sendMessageTest() {
    	EmailServiceMock emailNotifier = new EmailServiceMock();
        String receiptEmail = "thanksfordemo@gmail.com";
        String subject = "Hi test message";
        String message = "Test";
        assertTrue(emailNotifier.sendMessage(receiptEmail, subject, message));
    }

    @Test
    void setCredentialsTest() throws AwsSecretsManagerException {
    	EmailServiceMock emailNotifier = new EmailServiceMock();
        assertNotNull(emailNotifier.setCredentials());
    }
}
