package com.app.group15.Persistence;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private final static Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private static MysqlDataSource dataSource;
    private static String URL;
    private static String PSWD;
    private static String USER_NAME;

    public DatabaseManager() {

    }

    private static void getDetails() throws AwsSecretsManagerException {
    	
        DatabaseDetails databaseDetails = AwsSecretsManagerUtility.getDatabaseDetails();
        URL = "jdbc:mysql://" + databaseDetails.getHost() + ":" + databaseDetails.getPort() + "/"
                + databaseDetails.getDbName() + "?useSSL=false&serverTimezone=UTC";
        PSWD = databaseDetails.getPassword();
        USER_NAME = databaseDetails.getUserName();
    	
        
    }

    public static MysqlDataSource getDataSource() throws AwsSecretsManagerException {

        try {

            if (dataSource == null) {
                getDetails();
                dataSource = new MysqlDataSource();
                dataSource.setUrl(URL);
                dataSource.setPassword(PSWD);
                dataSource.setUser(USER_NAME);
            }

        } catch (AwsSecretsManagerException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        return dataSource;

    }


}
