package com.app.group15.PasswordPolicyManagement;


import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.UserAbstractDao;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PasswordPolicyHistoryConstraint implements IPasswordPolicyValidator, IUserPasswordHistoryAbstractDaoInjector {

    private UserPasswordHistoryAbstractDao passwordHistoryDao;
    private UserAbstractDao userDao;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean isPasswordValid(String password) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(password)) {
            List<UserPasswordHistory> previousPasswords = passwordHistoryDao.getPasswordHistory(this.userId);
            List<String> historyPasswords = new ArrayList<>();
            if (this.userId != -1) {
                String currentPassword = userDao.getUserPassword(this.userId);
                if (currentPassword.equals(password)) {
                    return false;
                }
            }

            previousPasswords.forEach((pass) -> historyPasswords.add(pass.getHistoryPassword()));
            return !historyPasswords.contains(password);
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "Password is null or user id is invalid");
        }
        return false;
    }

    @Override
    public void injectUserPasswordHistoryAbstractDao(UserPasswordHistoryAbstractDao passwordHistoryDao) {
        this.passwordHistoryDao = passwordHistoryDao;
    }

    @Override
    public void injectUserDao(UserAbstractDao userDao) {
        this.userDao = userDao;
    }

}
