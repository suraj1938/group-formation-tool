package com.app.group15.PasswordPolicyManagement;

public class PasswordPolicyMinUpperCaseInjector {
	
	private PasswordPolicyMinUpperCase passwordPolicyMinUpperCase;
	
	public PasswordPolicyMinUpperCaseInjector() {
		passwordPolicyMinUpperCase=new PasswordPolicyMinUpperCase();
		passwordPolicyMinUpperCase.injectPasswordPolicyAbstractDao(new PasswordPolicyDao());
	}

	public PasswordPolicyMinUpperCase getPasswordPolicyMinUpperCase() {
		return passwordPolicyMinUpperCase;
	}
	
	

}
