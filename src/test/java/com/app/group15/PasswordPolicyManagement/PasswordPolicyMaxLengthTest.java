package com.app.group15.PasswordPolicyManagement;

import org.junit.jupiter.api.Test;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.sql.SQLException;

class PasswordPolicyMaxLengthTest {
	
	private IPasswordPolicyValidator passwordPolicyValidator;

    @Test
    void isPasswordValid() throws SQLException, AwsSecretsManagerException {
        PasswordPolicyMaxLength policy=new PasswordPolicyMaxLength();
    	policy.injectPasswordPolicyAbstractDao(new PasswordPolicyDaoMock());
    	passwordPolicyValidator=policy;
    	assertEquals(passwordPolicyValidator.isPasswordValid("123456789111"),false);
    	assertEquals(passwordPolicyValidator.isPasswordValid("12355667"),true);

    }


}
