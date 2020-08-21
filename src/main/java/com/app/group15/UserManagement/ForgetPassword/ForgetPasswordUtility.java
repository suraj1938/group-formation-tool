package com.app.group15.UserManagement.ForgetPassword;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ForgetPasswordUtility {


    public static String generateForgotPasswordToken() {
        String baseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"
                + "-;!@#$%^&*";

        StringBuilder tokenBuilder = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            int j = (int) (baseString.length() * Math.random());
            tokenBuilder.append(baseString.charAt(j));
        }
        return tokenBuilder.toString();
    }

    public static long getTimeDifference(Date tokenGenerationDate) throws ParseException {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDate = sdf.parse(sdf.format(currentDate));
        long difference = currentDate.getTime() - tokenGenerationDate.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(difference);
        return minutes;


    }
}
