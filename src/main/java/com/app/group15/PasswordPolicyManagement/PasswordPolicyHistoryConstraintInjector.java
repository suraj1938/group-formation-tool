package com.app.group15.PasswordPolicyManagement;import com.app.group15.Config.AppConfig;
import com.app.group15.UserManagement.UserDaoInjectorService;

public class PasswordPolicyHistoryConstraintInjector {
	
	private PasswordPolicyHistoryConstraint passwordPolicyHistoryConstraint;
	
	public PasswordPolicyHistoryConstraintInjector() {
		passwordPolicyHistoryConstraint=new PasswordPolicyHistoryConstraint();
		passwordPolicyHistoryConstraint.injectUserPasswordHistoryAbstractDao(new UserPasswordHistoryDao());
		passwordPolicyHistoryConstraint.injectUserDao(new UserDaoInjectorService().getUserDao());
	}

	public PasswordPolicyHistoryConstraint getPasswordPolicyHistoryConstraint() {
		return passwordPolicyHistoryConstraint;
	}
	
	

}
