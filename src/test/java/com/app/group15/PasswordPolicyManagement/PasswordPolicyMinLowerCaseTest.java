package com.app.group15.PasswordPolicyManagement;

import org.junit.jupiter.api.Test;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.sql.SQLException;

class PasswordPolicyMinLowerCaseTest {
	private IPasswordPolicyValidator passwordPolicyValidator;


    @Test
    void isPasswordValid() throws SQLException, AwsSecretsManagerException {
    	PasswordPolicyMinLowerCase policy=new PasswordPolicyMinLowerCase();
     	policy.injectPasswordPolicyAbstractDao(new PasswordPolicyDaoMock());
     	passwordPolicyValidator=policy;
     	assertEquals(passwordPolicyValidator.isPasswordValid("uTTTTTTT"),false);
     	assertEquals(passwordPolicyValidator.isPasswordValid("uuuuyyyy"),true);

    }


}
