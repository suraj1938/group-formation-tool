package com.app.group15.PasswordPolicyManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.UserDaoMock;

public class PasswordPolicyHistoryConstraintTest {
	private IPasswordPolicyValidator passwordPolicyValidator;


    @Test
    void isPasswordValid() throws SQLException, AwsSecretsManagerException {
    	PasswordPolicyHistoryConstraint policy=new PasswordPolicyHistoryConstraint();
     	policy.injectUserDao(new UserDaoMock());
     	policy.injectUserPasswordHistoryAbstractDao(new UserPasswordHistoryDaoMock());
     	passwordPolicyValidator=policy;
     	assertEquals(passwordPolicyValidator.isPasswordValid("pass"),false);
     	assertEquals(passwordPolicyValidator.isPasswordValid("uuuuyyyy"),true);

    }

}
