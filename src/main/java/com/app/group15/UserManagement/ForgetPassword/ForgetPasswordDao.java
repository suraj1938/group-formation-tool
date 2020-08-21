package com.app.group15.UserManagement.ForgetPassword;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.PasswordPolicyManagement.UserPasswordHistory;
import com.app.group15.PasswordPolicyManagement.UserPasswordHistoryAbstractDao;
import com.app.group15.Persistence.DatabaseManager;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static com.app.group15.Utility.DatabaseQueriesUtility.*;

public class ForgetPasswordDao extends ForgetPasswordAbstractDao implements IForgetPasswordDaoInjector {

    private UserPasswordHistoryAbstractDao passwordHistoryDao;

    @Override
    public boolean checkIfTokenAlreadyExists(int id) throws SQLException, AwsSecretsManagerException {
        boolean exist = false;
        String query = CHECK_IF_TOKEN_EXISTS;

        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    if (id == result.getInt("id")) {
                        exist = true;
                    }
                }
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return exist;
    }

    @Override
    public boolean deleteForgotPasswordToken(int id) throws SQLException, AwsSecretsManagerException {
        String query = DELETE_TOKEN;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setInt(1, id);
                statement.executeUpdate();
                connection.commit();
                return true;
            } catch (Exception e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                }
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        return false;
    }

    public boolean insertForgotPasswordToken(int id, String token) throws SQLException, AwsSecretsManagerException {
        String query = INSERT_TOKEN;
        java.util.Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = sdf.format(date);
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setInt(1, id);
                statement.setString(2, token);
                statement.setString(3, currentDateTime);
                statement.executeUpdate();
                connection.commit();
            } catch (Exception e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                }
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            }

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return true;

    }

    @Override
    public Map<String, String> getUserFromToken(String token) throws SQLException, AwsSecretsManagerException {
        String query = GET_USER_FROM_TOKEN;
        HashMap<String, String> row = new HashMap<>();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    row.put("dateTime", result.getString("token_generation_date_time"));
                    row.put("id", String.valueOf(result.getInt("id")));
                }
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return row;
    }


    @Override
    public boolean updateUserPassword(int userId, String password) throws SQLException, AwsSecretsManagerException {
        String currentPassword = getUserPassword(userId);
        String query = UPDATE_PASSWORD;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                statement.setString(1, password);
                statement.setInt(2, userId);
                statement.executeUpdate();
                connection.commit();
                passwordHistoryDao.savePasswordHistory(createPasswordHistory(currentPassword, userId));
                return true;
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                    throw e;
                }

            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;

    }

    private String getUserPassword(int userId) throws SQLException, AwsSecretsManagerException {
        String query = GET_USER_PASSWORD;
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                return result.getString("password");
            }

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return null;

    }

    private UserPasswordHistory createPasswordHistory(String password, int userId) {
        UserPasswordHistory passwordHistory = new UserPasswordHistory();
        passwordHistory.setHistoryPassword(password);
        passwordHistory.setUserId(userId);
        return passwordHistory;

    }


    @Override
    public void injectPasswordHistoryDao(UserPasswordHistoryAbstractDao passwordHistoryDao) {
        this.passwordHistoryDao = passwordHistoryDao;
    }

}
