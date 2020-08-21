package com.app.group15.PasswordPolicyManagement;

public class PasswordPolicyMinLengthInjector {
	private PasswordPolicyMinLength passwordPolicyMinLength;
	
	public PasswordPolicyMinLengthInjector(){
		passwordPolicyMinLength =new PasswordPolicyMinLength();
		passwordPolicyMinLength.injectPasswordPolicyAbstractDao(new PasswordPolicyDao());
	}

	public PasswordPolicyMinLength getPasswordPolicyMinLength() {
		return passwordPolicyMinLength;
	}
	
	

}
