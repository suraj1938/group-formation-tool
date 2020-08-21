package com.app.group15.ExceptionHandler;

public class AllowedRolesNotSetException extends Exception {

    private static final long serialVersionUID = 1L;

    public AllowedRolesNotSetException(String msg) {
        super(msg);
    }
}
