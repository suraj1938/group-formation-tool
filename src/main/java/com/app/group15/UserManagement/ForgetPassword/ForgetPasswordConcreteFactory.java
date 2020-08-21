package com.app.group15.UserManagement.ForgetPassword;

public class ForgetPasswordConcreteFactory implements IForgetPasswordAbstractFactory {

	private static IForgetPasswordAbstractFactory forgetPasswordAbstractFactory;

	public static IForgetPasswordAbstractFactory getInstance() {
		if (null == ForgetPasswordConcreteFactory.getUniqueInstance()) {
			forgetPasswordAbstractFactory = new ForgetPasswordConcreteFactory();
		}
		return ForgetPasswordConcreteFactory.forgetPasswordAbstractFactory;
	}

	private static IForgetPasswordAbstractFactory getUniqueInstance() {
		return forgetPasswordAbstractFactory;
	}

	@Override
	public ForgetPasswordAbstractDao getForgetPasswordDao() {
		return new ForgetPasswordDao();
	}

	@Override
	public IForgetPasswordService getForgetPasswordService() {
		return new ForgetPasswordServiceInjector().getForgetPasswordService();
	}
}
