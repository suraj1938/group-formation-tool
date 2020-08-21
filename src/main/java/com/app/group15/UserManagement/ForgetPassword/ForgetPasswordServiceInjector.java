package com.app.group15.UserManagement.ForgetPassword;

import com.app.group15.UserManagement.UserDaoInjectorService;

public class ForgetPasswordServiceInjector {


    private ForgetPasswordService forgetPasswordService;

    public ForgetPasswordServiceInjector() {
        forgetPasswordService = new ForgetPasswordService();
        forgetPasswordService.injectForgetPasswordDao(new ForgetPasswordDaoInjector().getForgetPasswordDao());
        forgetPasswordService.injectUserDao(new UserDaoInjectorService().getUserDao());
    }

    public ForgetPasswordService getForgetPasswordService() {
        return forgetPasswordService;
    }

}
