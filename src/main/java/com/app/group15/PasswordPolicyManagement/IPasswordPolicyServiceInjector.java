package com.app.group15.PasswordPolicyManagement;

import java.util.List;

public interface IPasswordPolicyServiceInjector {
    void injectPasswordPolicy(List<IPasswordPolicyValidator> passwordPolicyList);

}
