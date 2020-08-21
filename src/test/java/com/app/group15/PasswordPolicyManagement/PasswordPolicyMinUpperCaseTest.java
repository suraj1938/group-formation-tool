package com.app.group15.PasswordPolicyManagement;

import org.junit.jupiter.api.Test;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

class PasswordPolicyMinUpperCaseTest {
	private IPasswordPolicyValidator passwordPolicyValidator;

    @Test
    void isPasswordValid() throws SQLException, AwsSecretsManagerException {
    	PasswordPolicyMinUpperCase policy=new PasswordPolicyMinUpperCase();
     	policy.injectPasswordPolicyAbstractDao(new PasswordPolicyDaoMock());
     	passwordPolicyValidator=policy;
     	assertEquals(passwordPolicyValidator.isPasswordValid("uuuuuuu"),false);
     	assertEquals(passwordPolicyValidator.isPasswordValid("uuUUUUUUU"),true);


    }
}
