package com.app.group15.PasswordPolicyManagement;

public class PasswordPolicyMinLowerCaseInjector {
	
	private PasswordPolicyMinLowerCase passwordPolicyMinLowerCase;
	
	public PasswordPolicyMinLowerCaseInjector() {
		passwordPolicyMinLowerCase=new PasswordPolicyMinLowerCase();
		passwordPolicyMinLowerCase.injectPasswordPolicyAbstractDao(new PasswordPolicyDao());
	}

	public PasswordPolicyMinLowerCase getPasswordPolicyMinLowerCase() {
		return passwordPolicyMinLowerCase;
	}
	

}
