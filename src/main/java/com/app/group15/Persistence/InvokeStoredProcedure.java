package com.app.group15.Persistence;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class InvokeStoredProcedure {

    private Connection connection;
    private String procedureName;
    private CallableStatement statement;


    public InvokeStoredProcedure(String procedureName) throws SQLException, AwsSecretsManagerException {
        this.procedureName = procedureName;
        prepareConnection();
        prepareStatement();
    }

    public void prepareConnection() throws SQLException, AwsSecretsManagerException {
        connection = DatabaseManager.getDataSource().getConnection();
    }

    public void prepareStatement() throws SQLException {
        statement = connection.prepareCall("{call " + procedureName + " }");
    }

    public void closeConnection() throws SQLException {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
        } catch (SQLException e) {

            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    public void setParameter(int paramIndex, String value) throws SQLException {
        statement.setString(paramIndex, value);
    }

    public void setParameter(int paramIndex, long value) throws SQLException {
        statement.setLong(paramIndex, value);
    }

    public void registerOutputParameterLong(int paramIndex) throws SQLException {
        statement.registerOutParameter(paramIndex, java.sql.Types.BIGINT);
    }

    public int getOutputParameter(int paramIndex) throws SQLException {
        return statement.getInt(paramIndex);
    }

    public ResultSet executeWithResults() throws SQLException {
        if (statement.execute()) {
            return statement.getResultSet();
        }
        return null;
    }

    public void execute() throws SQLException {
        statement.execute();
    }

}
