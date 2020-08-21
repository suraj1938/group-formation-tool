package com.app.group15.PasswordPolicyManagement;

import org.junit.jupiter.api.Test;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.sql.SQLException;

class PasswordPolicyCharNotAllowedTest {
	
	private IPasswordPolicyValidator passwordPolicyValidator;

    @Test
    void isPasswordValidTest() throws SQLException, AwsSecretsManagerException {
    	PasswordPolicyCharNotAllowed policy =new PasswordPolicyCharNotAllowed();
    	policy.injectPasswordPolicyAbstractDao(new PasswordPolicyDaoMock());
    	passwordPolicyValidator=policy;
    	assertEquals(passwordPolicyValidator.isPasswordValid("123%"),false);
    	assertEquals(passwordPolicyValidator.isPasswordValid("123"),true);
        
    }
}
