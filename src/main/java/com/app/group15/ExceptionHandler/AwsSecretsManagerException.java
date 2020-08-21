package com.app.group15.ExceptionHandler;

public class AwsSecretsManagerException extends Exception{
	private static final long serialVersionUID = 1L;

    public AwsSecretsManagerException(String msg,Exception e) {
        super(msg,e);
    }

}
