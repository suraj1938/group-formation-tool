package com.app.group15.PasswordPolicyManagement;


import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PasswordPolicyServiceInjector {

    private PasswordPolicyDao passwordPolicyDao;
    private PasswordPolicyService passwordPolicyService;

    public PasswordPolicyServiceInjector() throws SQLException, AwsSecretsManagerException {

        passwordPolicyDao = new PasswordPolicyDao();
        List<PasswordPolicy> activePasswordPolicy = passwordPolicyDao.getActivePasswordPolicy();
        passwordPolicyService = new PasswordPolicyService();
        List<IPasswordPolicyValidator> activePolicyList = new ArrayList();

        activePasswordPolicy.forEach(policy -> {
            switch (policy.getPolicyId()) {
                case 1:
                    activePolicyList.add(new PasswordPolicyMinLengthInjector().getPasswordPolicyMinLength());
                    break;
                case 2:
                    activePolicyList.add(new PasswordPolicyMaxLengthInjector().getPasswordPolicyMaxLength());
                    break;
                case 3:
                    activePolicyList.add(new PasswordPolicyMinUpperCaseInjector().getPasswordPolicyMinUpperCase());
                    break;
                case 4:
                    activePolicyList.add(new PasswordPolicyMinLowerCaseInjector().getPasswordPolicyMinLowerCase());
                    break;
                case 5:
                    activePolicyList.add(new PasswordPolicyMinSpecialCharInjector().getPasswordPolicyMinSpecialChar());
                    break;
                case 6:
                    activePolicyList.add(new PasswordPolicyCharNotAllowedInjector().getPasswordPolicyCharNotAllowed());
                    break;
                case 7:
                    activePolicyList.add(new PasswordPolicyHistoryConstraintInjector().getPasswordPolicyHistoryConstraint());
                    break;
            }
        });
        passwordPolicyService.injectPasswordPolicy(activePolicyList);
    }

    public PasswordPolicyService getPasswordPolicyService() {
        return passwordPolicyService;
    }
}
