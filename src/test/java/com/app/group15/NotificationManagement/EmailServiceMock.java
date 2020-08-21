package com.app.group15.NotificationManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.AwsSecretsManagerUtility;
import com.app.group15.Utility.ServiceUtility;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailServiceMock {

    static Properties springProperties;

    static {
        springProperties = AppConfig.getInstance().getProperties();
    }

    private JavaMailSenderImpl javaMailSenderMock;

    public boolean sendMessage(String receiptEmail, String subject, String message) {
        return ServiceUtility.isNotNull(receiptEmail) && ServiceUtility.isNotNull(subject) && ServiceUtility.isNotNull(message);
    }

    public JavaMailSenderImpl setCredentials() throws AwsSecretsManagerException {
        javaMailSenderMock = new JavaMailSenderImpl();
        javaMailSenderMock.setHost(springProperties.getProperty("mail.host"));
        javaMailSenderMock.setPort(Integer.parseInt(springProperties.getProperty("mail.port")));
        javaMailSenderMock.setUsername(AwsSecretsManagerUtility.getSmtpEmail());
        javaMailSenderMock.setPassword(AwsSecretsManagerUtility.getSmtpPassword());
        return javaMailSenderMock;
    }
}
