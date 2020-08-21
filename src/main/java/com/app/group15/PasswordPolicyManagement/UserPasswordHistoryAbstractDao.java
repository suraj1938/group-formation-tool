package com.app.group15.PasswordPolicyManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;
import java.util.List;

public class UserPasswordHistoryAbstractDao implements IDao {

    @Override
    public Persistence get(int id) {
        return null;
    }

    
    public List getPasswordHistory(int userId) throws SQLException, AwsSecretsManagerException {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public int save(Persistence t) {
        return 0;
    }

    @Override
    public void update(Persistence t, int id) {

    }

    @Override
    public void delete(int id) {

    }

    public void savePasswordHistory(UserPasswordHistory passwordHistory) throws SQLException, AwsSecretsManagerException {

    }

}
