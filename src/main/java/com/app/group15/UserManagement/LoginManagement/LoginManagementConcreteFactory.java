package com.app.group15.UserManagement.LoginManagement;


public class LoginManagementConcreteFactory implements ILoginManagementAbstractFactory{

	private static ILoginManagementAbstractFactory loginManagementConcreteFactory;

	public static ILoginManagementAbstractFactory getInstance() {
		if (null==LoginManagementConcreteFactory.getUniqueInstance()){
			loginManagementConcreteFactory=new LoginManagementConcreteFactory();
		}
		return LoginManagementConcreteFactory.loginManagementConcreteFactory;
	}

	private static ILoginManagementAbstractFactory getUniqueInstance() {
		return loginManagementConcreteFactory;
	}

	@Override
	public ILoginService getLoginService() {
		return new LoginServiceInjector().getLoginService();
	}
}
