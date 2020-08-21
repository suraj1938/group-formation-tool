package com.app.group15.PasswordPolicyManagement;

import com.app.group15.UserManagement.UserAbstractDao;

public interface IUserPasswordHistoryAbstractDaoInjector {
	
	public void injectUserPasswordHistoryAbstractDao (UserPasswordHistoryAbstractDao passwordHistoryAbstractDao);
	public void injectUserDao(UserAbstractDao userDao);

}
