package com.app.group15.UserManagement.LoginManagement;


import com.app.group15.UserManagement.UserDaoMock;

public class LoginServiceInjectorForTest {

    private LoginService loginService;

    public LoginServiceInjectorForTest() {
        loginService = new LoginService();
        loginService.injectUserDao(new UserDaoMock());
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
