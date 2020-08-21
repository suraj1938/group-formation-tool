package com.app.group15.UserManagement.SignupManagement;

public class SignupManagementConcreteFactory implements ISignupManagementAbstractFactory{

	private static ISignupManagementAbstractFactory signupManagementAbstractFactory;

	public static ISignupManagementAbstractFactory getInstance(){
		if (null==SignupManagementConcreteFactory.getUniqueInstance()){
			SignupManagementConcreteFactory.signupManagementAbstractFactory=new SignupManagementConcreteFactory();
		}
		return signupManagementAbstractFactory;
	}

	private static ISignupManagementAbstractFactory getUniqueInstance(){
		return signupManagementAbstractFactory;
	}

	@Override
	public ISignupService getSignupService() {
		return new SignUpServiceInjector().getSignUpService();
	}
}
