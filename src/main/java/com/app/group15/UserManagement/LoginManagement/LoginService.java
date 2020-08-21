package com.app.group15.UserManagement.LoginManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;
import com.app.group15.UserManagement.UserAbstractDao;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.logging.Level;

public class LoginService implements ILoginService, ILoginServiceInjector {
    private UserAbstractDao userDao;
    private String invalidInput = "Invalid input";

    public boolean validateLogin(String bannerId, String password) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(bannerId) && ServiceUtility.isNotNull(password)) {
            User user = userDao.getUserByBannerId(bannerId);
            if (user.getBannerId() != null && user.getPassword() != null) {
                return bannerId.equals(user.getBannerId()) && password.equals(user.getPassword());
            } else {
                return false;
            }
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return false;
        }
    }

    @Override
    public void injectUserDao(UserAbstractDao userDao) {
        if (ServiceUtility.isNotNull(userDao)) {
            if (ServiceUtility.isNotNull(userDao)) {
                this.userDao = userDao;
            } else {
                GroupFormationToolLogger.log(Level.SEVERE, "User Dao Dependency not injected ");
            }
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }

    }

}
