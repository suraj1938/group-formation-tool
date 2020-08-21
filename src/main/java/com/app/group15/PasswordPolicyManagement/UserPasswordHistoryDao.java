package com.app.group15.PasswordPolicyManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.InvokeStoredProcedure;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class UserPasswordHistoryDao extends UserPasswordHistoryAbstractDao {

    IPasswordPolicyAbstractFactory passwordPolicyAbstractFactory = AppConfig.getInstance().getPasswordPolicyAbstractFactory();

    @Override
    public List getPasswordHistory(int userId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spGetUserPasswordHistory(?)");
            invokeStoredProcedure.setParameter(1, userId);
            ResultSet results = invokeStoredProcedure.executeWithResults();
            List<UserPasswordHistory> passwordHistoryList = new ArrayList<>();
            if (results != null) {
                while (results.next()) {
                    UserPasswordHistory passwordHistory = (UserPasswordHistory) passwordPolicyAbstractFactory.getUserPasswordHistoryModel();
                    passwordHistory.setHistoryPassword(results.getString("password"));
                    passwordHistory.setUserId(results.getInt("user_id"));
                    passwordHistoryList.add(passwordHistory);

                }
            }
            return passwordHistoryList;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            invokeStoredProcedure.closeConnection();
        }


    }

    @Override
    public void savePasswordHistory(UserPasswordHistory passwordHistory) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spAddUserPasswordHistory(?,?)");
            invokeStoredProcedure.setParameter(1, passwordHistory.getUserId());
            invokeStoredProcedure.setParameter(2, passwordHistory.getHistoryPassword());
            invokeStoredProcedure.execute();
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            invokeStoredProcedure.closeConnection();
        }


    }
}
