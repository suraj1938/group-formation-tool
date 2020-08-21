package com.app.group15.UserManagement.ForgetPassword;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;
import com.app.group15.UserManagement.UserDao;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;

public class ForgetPasswordService implements IForgetPasswordService, IForgetPasswordServiceInjector {

    private ForgetPasswordAbstractDao forgetPasswordDao;
    private UserDao userDao;
    private String invalidInput = "Invalid invalid";

    @Override
    public void generateToken(String bannerId, HttpServletRequest request) throws SQLException, UnsupportedEncodingException, AwsSecretsManagerException {

        try {
            if (ServiceUtility.isNotNull(bannerId) && ServiceUtility.isNotNull(request)) {
                User user = userDao.getUserByBannerId(bannerId);

                String token = ForgetPasswordUtility.generateForgotPasswordToken();
                if (forgetPasswordDao.checkIfTokenAlreadyExists(user.getId())) {
                    forgetPasswordDao.deleteForgotPasswordToken(user.getId());
                }

                forgetPasswordDao.insertForgotPasswordToken(user.getId(), token);

                String urlGenerated = request.getRequestURL().toString();
                urlGenerated = urlGenerated.substring(0, urlGenerated.lastIndexOf('/'));
                token = URLEncoder.encode(token, StandardCharsets.UTF_8.toString());
                urlGenerated += "/auth/validateToken/?to=" + token;

                String subject = "CATME Password Change request";
                String body = "Click this link to change password: " + urlGenerated;
                AppConfig.getInstance().getEmailNotifier().sendMessage(user.getEmail(), subject, body);
            } else {
                GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            }
        } catch (UnsupportedEncodingException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
       
    }

    @Override
    public boolean verifyToken(String token) throws  SQLException, ParseException, AwsSecretsManagerException {

        if (ServiceUtility.isNotNull(token)) {
            if (ServiceUtility.isNotNull(token)) {

                boolean validated = false;
                Map<String, String> user = forgetPasswordDao.getUserFromToken(token);
                String tokenGenerationDateTime = user.get("dateTime");
                Date tokenGenerationDate = null;
                try {
                    tokenGenerationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tokenGenerationDateTime);
                    long minutes = ForgetPasswordUtility.getTimeDifference(tokenGenerationDate);
                    if (minutes > 60) {
                        forgetPasswordDao.deleteForgotPasswordToken(Integer.parseInt(user.get("id")));
                        validated = false;
                    }
                    validated = true;
                } catch (ParseException e) {
                    GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                    throw e;
                }
 

                return validated;

            } else {
                GroupFormationToolLogger.log(Level.SEVERE, "Invalid input");
                return false;
            }
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return false;
        }
    }

    @Override
    public boolean deleteForgotPasswordToken(int id) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(id)) {
            boolean deleted = forgetPasswordDao.deleteForgotPasswordToken(id);
            return deleted;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return false;
        }
    }

    @Override
    public Map<String, String> getUserFromToken(String token) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(token)) {
            Map<String, String> user = forgetPasswordDao.getUserFromToken(token);
            return user;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return null;


        }
    }

    @Override
    public boolean updateUserPassword(int id, String newPassword) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(newPassword)) {
            boolean updated = forgetPasswordDao.updateUserPassword(id, newPassword);
            return updated;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return false;
        }
    }

    @Override
    public void injectUserDao(UserDao userDao) {
        if (ServiceUtility.isNotNull(userDao)) {
            if (ServiceUtility.isNotNull(userDao)) {
                this.userDao = userDao;
            } else {
                GroupFormationToolLogger.log(Level.SEVERE, "userDao injection problems in ForgetPasswordService");
            }
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
    }

    @Override
    public void injectForgetPasswordDao(ForgetPasswordAbstractDao forgetPasswordDao) {
        if (ServiceUtility.isNotNull(forgetPasswordDao)) {
            if (ServiceUtility.isNotNull(forgetPasswordDao)) {
                this.forgetPasswordDao = forgetPasswordDao;
            } else {
                GroupFormationToolLogger.log(Level.SEVERE, "forgetPasswordDao injection problems in ForgetPasswordService");
            }
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
    }
}
