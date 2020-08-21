package com.app.group15.PasswordPolicyManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;

public interface IPasswordPolicyAbstractFactory {

    public Persistence getPasswordPolicyModel();
    
    public PasswordPolicyValidationResult getPasswordPolicyValidationResultModel();

    public Persistence getUserPasswordHistoryModel();

    public PasswordPolicyAbstractDao getPasswordPolicyDao();

    public UserPasswordHistoryAbstractDao getUserPasswordHistoryDao();

    public IPasswordPolicyService getPasswordPolicyService() throws SQLException, AwsSecretsManagerException;

    public IPasswordPolicyValidator getPasswordPolicyMaxLength();

    public IPasswordPolicyValidator getPasswordPolicyMinLength();

    public IPasswordPolicyValidator getPasswordPolicyMinLowerCase();

    public IPasswordPolicyValidator getPasswordPolicyMinSpecialChar();

    public IPasswordPolicyValidator getPasswordPolicyMinUpperCase();

    public IPasswordPolicyValidator getPasswordPolicyCharNotAllowed();

    public IPasswordPolicyValidator getPasswordPolicyHistoryConstrain();


}
