package com.app.group15.PasswordPolicyManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class PasswordPolicyMinLowerCase implements IPasswordPolicyValidator,IPasswordPolicyAbstractDaoInjector {

    private PasswordPolicyAbstractDao passwordPolicyDao;

    @Override
    public boolean isPasswordValid(String password) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(password)) {
            List<PasswordPolicy> passwordPolicyList = passwordPolicyDao.getAll();

            int minimumNumberOfLowercaseAllowed = Integer.parseInt(passwordPolicyList.get(3).getPolicyValue());
            int countLowercase = 0;

            for (int i = 0; i < password.length(); i++) {
                if (Character.isLowerCase(password.charAt(i))) {
                    countLowercase++;
                }
            }

            return countLowercase >= minimumNumberOfLowercaseAllowed;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "Password is null");
        }
        return false;
    }

	@Override
	public void injectPasswordPolicyAbstractDao(PasswordPolicyAbstractDao dao) {
		this.passwordPolicyDao=dao;
		
	}

}
