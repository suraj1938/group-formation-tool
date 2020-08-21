package com.app.group15.PasswordPolicyManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public class UserPasswordHistoryDaoMock extends UserPasswordHistoryAbstractDao{
	
	public List getPasswordHistory(int userId) throws SQLException, AwsSecretsManagerException {
		List<UserPasswordHistory> previousPasswords =new ArrayList();
		UserPasswordHistory history=new UserPasswordHistory();
		history.setHistoryPassword("pass");
		history.setUserId(1);
		previousPasswords.add(history);
		return previousPasswords;
    }


}
