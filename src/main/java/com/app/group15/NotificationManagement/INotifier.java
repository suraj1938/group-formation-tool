package com.app.group15.NotificationManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public interface INotifier {
    boolean sendMessage(String email, String subject, String message) throws AwsSecretsManagerException;

    boolean setCredentials() throws AwsSecretsManagerException;
}
