package com.app.group15.PasswordPolicyManagement;

public class PasswordPolicyCharNotAllowedInjector {
	private PasswordPolicyCharNotAllowed passwordPolicyCharNotAllowed;
	
	public PasswordPolicyCharNotAllowedInjector() {
		passwordPolicyCharNotAllowed=new PasswordPolicyCharNotAllowed();
		passwordPolicyCharNotAllowed.injectPasswordPolicyAbstractDao(new PasswordPolicyDao());
	}

	public PasswordPolicyCharNotAllowed getPasswordPolicyCharNotAllowed() {
		return passwordPolicyCharNotAllowed;
	}

	
	
	

}
