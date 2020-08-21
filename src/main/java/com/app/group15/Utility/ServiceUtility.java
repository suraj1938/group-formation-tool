package com.app.group15.Utility;


public class ServiceUtility {

    public static boolean isNotNull(String validateString) {
        return null != validateString && validateString.length() >= 0;
    }

    public static boolean isNotNull(Object validateObject) {
        return null != validateObject;
    }

    public static boolean isValidInt(int validateInt) {
        return validateInt >= 0;
    }


}
