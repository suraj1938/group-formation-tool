package com.app.group15.UserManagement.LoginManagement;

import com.app.group15.UserManagement.UserAbstractDao;

public interface ILoginServiceInjector {

    void injectUserDao(UserAbstractDao userDao);
}
