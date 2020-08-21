package com.app.group15.UserManagement.ForgetPassword;

import com.app.group15.UserManagement.UserDao;

public interface IForgetPasswordServiceInjector {

    void injectUserDao(UserDao userDao);

    void injectForgetPasswordDao(ForgetPasswordAbstractDao forgetPasswordDao);
}
