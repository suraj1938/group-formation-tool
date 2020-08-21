package com.app.group15.ExceptionHandler;

import com.app.group15.Utility.GroupFormationToolLogger;

import java.util.logging.Level;

public class GroupFormationToolExceptionHandler extends Exception {

    public GroupFormationToolExceptionHandler(String message, Throwable tr) {
        super(message, tr);
        GroupFormationToolLogger.log(Level.SEVERE, tr.getMessage(), new Exception(tr));
    }

}