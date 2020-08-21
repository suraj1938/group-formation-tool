package com.app.group15.PasswordPolicyManagement;

public class PasswordPolicyMinSpecialCharInjector {
	
	private PasswordPolicyMinSpecialChar passwordPolicyMinSpecialChar;
	
	public PasswordPolicyMinSpecialCharInjector() {
		passwordPolicyMinSpecialChar=new PasswordPolicyMinSpecialChar();
		passwordPolicyMinSpecialChar.injectPasswordPolicyAbstractDao(new PasswordPolicyDao());
	}

	public PasswordPolicyMinSpecialChar getPasswordPolicyMinSpecialChar() {
		return passwordPolicyMinSpecialChar;
	}

	public void setPasswordPolicyMinSpecialChar(PasswordPolicyMinSpecialChar passwordPolicyMinSpecialChar) {
		this.passwordPolicyMinSpecialChar = passwordPolicyMinSpecialChar;
	}
	
	

}
