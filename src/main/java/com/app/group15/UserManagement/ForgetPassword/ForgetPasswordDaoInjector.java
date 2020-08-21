package com.app.group15.UserManagement.ForgetPassword;

import com.app.group15.PasswordPolicyManagement.UserPasswordHistoryDao;

public class ForgetPasswordDaoInjector {

    private ForgetPasswordDao forgetPasswordDao;

    public ForgetPasswordDaoInjector() {
        forgetPasswordDao = new ForgetPasswordDao();
        forgetPasswordDao.injectPasswordHistoryDao(new UserPasswordHistoryDao());
    }

    public ForgetPasswordDao getForgetPasswordDao() {
        return forgetPasswordDao;
    }
}
