package com.app.group15.UserManagement.SignupManagement;

import com.app.group15.UserManagement.UserDaoMock;

public class SignUpServiceInjectorForTest {
    private SignupService signUpService;

    public SignUpServiceInjectorForTest() {
        signUpService = new SignupService();
        signUpService.injectUserDao(new UserDaoMock());
    }

    public SignupService getSignUpService() {
        return signUpService;
    }

}
