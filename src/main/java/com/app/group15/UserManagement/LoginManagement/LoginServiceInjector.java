package com.app.group15.UserManagement.LoginManagement;

import com.app.group15.UserManagement.UserDaoInjectorService;

public class LoginServiceInjector {

    private LoginService loginService;

    public LoginServiceInjector() {
        loginService = new LoginService();
        loginService.injectUserDao(new UserDaoInjectorService().getUserDao());
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}
