package com.app.group15.UserManagement.SignupManagement;

import com.app.group15.UserManagement.UserDaoInjectorService;

public class SignUpServiceInjector {

    private SignupService signUpService;

    public SignUpServiceInjector() {
        signUpService = new SignupService();
        signUpService.injectUserDao(new UserDaoInjectorService().getUserDao());
    }

    public SignupService getSignUpService() {
        return signUpService;
    }

    public void setSignUpService(SignupService signUpService) {
        this.signUpService = signUpService;
    }


}
