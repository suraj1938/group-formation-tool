package com.app.group15.PasswordPolicyManagement;

import org.junit.jupiter.api.Test;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.sql.SQLException;

class PasswordPolicyMinLengthTest {
	
	private IPasswordPolicyValidator passwordPolicyValidator;

    @Test
    void isPasswordValid() throws SQLException, AwsSecretsManagerException {
    	 PasswordPolicyMinLength policy=new PasswordPolicyMinLength();
     	policy.injectPasswordPolicyAbstractDao(new PasswordPolicyDaoMock());
     	passwordPolicyValidator=policy;
     	assertEquals(passwordPolicyValidator.isPasswordValid("123"),false);
     	assertEquals(passwordPolicyValidator.isPasswordValid("12355667"),true);
    }
}
