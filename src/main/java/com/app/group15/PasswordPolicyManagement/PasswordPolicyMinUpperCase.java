package com.app.group15.PasswordPolicyManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class PasswordPolicyMinUpperCase implements IPasswordPolicyValidator,IPasswordPolicyAbstractDaoInjector {

    private PasswordPolicyAbstractDao passwordPolicyDao;

    @Override
    public boolean isPasswordValid(String password) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(password)) {
            
            List<PasswordPolicy> passwordPolicyList = passwordPolicyDao.getAll();

            int minimumNumberOfUppercaseAllowed = Integer.parseInt(passwordPolicyList.get(2).getPolicyValue());

            int countUppercase = 0;

            for (int i = 0; i < password.length(); i++) {
                if (Character.isUpperCase(password.charAt(i))) {
                    countUppercase++;
                }
            }

            return countUppercase >= minimumNumberOfUppercaseAllowed;
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
