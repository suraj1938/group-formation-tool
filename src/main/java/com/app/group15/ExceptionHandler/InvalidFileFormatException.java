package com.app.group15.ExceptionHandler;

public class InvalidFileFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidFileFormatException(String msg) {
        super(msg);
    }

}
