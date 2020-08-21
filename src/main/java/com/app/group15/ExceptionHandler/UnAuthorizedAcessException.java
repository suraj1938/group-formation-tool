package com.app.group15.ExceptionHandler;

public class UnAuthorizedAcessException extends Exception {

    private static final long serialVersionUID = 1L;

    public UnAuthorizedAcessException(String msg) {
        super(msg);
    }

}
