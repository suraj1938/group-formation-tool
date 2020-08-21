package com.app.group15.PasswordPolicyManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class PasswordPolicyMinSpecialChar implements IPasswordPolicyValidator,IPasswordPolicyAbstractDaoInjector {

    PasswordPolicyAbstractDao passwordPolicyDao;

    @Override
    public boolean isPasswordValid(String password) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(password)) {

 
            List<PasswordPolicy> passwordPolicyList = passwordPolicyDao.getAll();

            int minimumNumberOfSpecialCharAllowed = Integer.parseInt(passwordPolicyList.get(4).getPolicyValue());

            int countSpecialChar = 0;

            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);

                if (!Character.isDigit(c) && !Character.isLetter(c) && !Character.isWhitespace(c)) {
                    countSpecialChar++;
                }
            }

            return countSpecialChar >= minimumNumberOfSpecialCharAllowed;
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
