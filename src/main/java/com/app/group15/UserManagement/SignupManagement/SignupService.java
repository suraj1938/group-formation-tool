package com.app.group15.UserManagement.SignupManagement;

import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;
import com.app.group15.UserManagement.UserAbstractDao;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.logging.Level;

public class SignupService implements ISignupService, ISignUpServiceInjector {
    private UserAbstractDao userDao;
    private String invalidInput = "Invalid input";

    public boolean checkUserExists(String bannerId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(bannerId)) {
            User user = userDao.getUserByBannerId(bannerId);
            boolean response;

            response = user.getBannerId() != null;
            return response;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return false;
        }
    }

    public int createUser(User user, String role) throws AllowedRolesNotSetException, AwsSecretsManagerException, SQLException {
        if (ServiceUtility.isNotNull(user) && ServiceUtility.isNotNull(role)) {
            return userDao.saveUser(user, role);
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return -1;
    }

    @Override
    public void injectUserDao(UserAbstractDao userDao) {
        if (ServiceUtility.isNotNull(userDao)) {
            this.userDao = userDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "User Dao Dependency not injected ");
        }

    }

}
