package com.app.group15.UserManagement.ForgetPassword;

public interface IForgetPasswordAbstractFactory {
	ForgetPasswordAbstractDao getForgetPasswordDao();

	IForgetPasswordService getForgetPasswordService();
}
