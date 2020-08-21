package com.app.group15.PasswordPolicyManagement;

public class PasswordPolicyMaxLengthInjector {
	private PasswordPolicyMaxLength passwordPolicyMaxLength;
	
	public PasswordPolicyMaxLengthInjector() {
		passwordPolicyMaxLength=new PasswordPolicyMaxLength();
		passwordPolicyMaxLength.injectPasswordPolicyAbstractDao(new PasswordPolicyDao());
		
	}

	public PasswordPolicyMaxLength getPasswordPolicyMaxLength() {
		return passwordPolicyMaxLength;
	}
	
	

}
