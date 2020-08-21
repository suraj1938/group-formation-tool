package com.app.group15.PasswordPolicyManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;

public class PasswordPolicyConcreteFactory implements IPasswordPolicyAbstractFactory {

    private static IPasswordPolicyAbstractFactory passwordPolicyAbstractFactory;

    public static IPasswordPolicyAbstractFactory getInstance() {
        if (null == PasswordPolicyConcreteFactory.getUniqueInstance()) {
            passwordPolicyAbstractFactory = new PasswordPolicyConcreteFactory();
        }
        return PasswordPolicyConcreteFactory.passwordPolicyAbstractFactory;
    }

    private static IPasswordPolicyAbstractFactory getUniqueInstance() {
        return passwordPolicyAbstractFactory;
    }

    @Override
    public Persistence getPasswordPolicyModel() {
        return new PasswordPolicy();
    }

    @Override
    public PasswordPolicyValidationResult getPasswordPolicyValidationResultModel() {
        return new PasswordPolicyValidationResult();
    }

    @Override
    public Persistence getUserPasswordHistoryModel() {
        return new UserPasswordHistory();
    }

    @Override
    public PasswordPolicyAbstractDao getPasswordPolicyDao() {
        return new PasswordPolicyDao();
    }

    @Override
    public UserPasswordHistoryAbstractDao getUserPasswordHistoryDao() {
        return new UserPasswordHistoryDao();
    }

    @Override
    public IPasswordPolicyService getPasswordPolicyService() throws SQLException, AwsSecretsManagerException {
        return new PasswordPolicyServiceInjector().getPasswordPolicyService();
    }

    @Override
    public IPasswordPolicyValidator getPasswordPolicyMaxLength() {
        return new PasswordPolicyMaxLengthInjector().getPasswordPolicyMaxLength();
    }

    @Override
    public IPasswordPolicyValidator getPasswordPolicyMinLength() {
        return new PasswordPolicyMinLengthInjector().getPasswordPolicyMinLength();
    }

    @Override
    public IPasswordPolicyValidator getPasswordPolicyMinLowerCase() {
        return new PasswordPolicyMinLowerCaseInjector().getPasswordPolicyMinLowerCase();
    }

    @Override
    public IPasswordPolicyValidator getPasswordPolicyMinSpecialChar() {
        return new PasswordPolicyMinSpecialCharInjector().getPasswordPolicyMinSpecialChar();
    }

    @Override
    public IPasswordPolicyValidator getPasswordPolicyMinUpperCase() {
        return new PasswordPolicyMinUpperCaseInjector().getPasswordPolicyMinUpperCase();
    }

    @Override
    public IPasswordPolicyValidator getPasswordPolicyCharNotAllowed() {
        return new PasswordPolicyCharNotAllowedInjector().getPasswordPolicyCharNotAllowed();
    }

    @Override
    public IPasswordPolicyValidator getPasswordPolicyHistoryConstrain() {
        return new PasswordPolicyHistoryConstraintInjector().getPasswordPolicyHistoryConstraint();
    }
}
