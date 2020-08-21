package com.app.group15.PasswordPolicyManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;
import java.util.List;

public abstract class PasswordPolicyAbstractDao<T> implements IDao {

    @Override
    public Persistence get(int id) {
        return null;
    }

    @Override
    public abstract List<PasswordPolicy> getAll() throws SQLException, AwsSecretsManagerException;

    @Override
    public int save(Persistence persistence) {
        return 0;
    }

    @Override
    public void update(Persistence persistence, int id) {

    }

    @Override
    public void delete(int id) {

    }


    public abstract List<PasswordPolicy> getActivePasswordPolicy() throws SQLException, AwsSecretsManagerException;

}
