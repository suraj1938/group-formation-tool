package com.app.group15.UserManagement;

import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class UserService implements IUserService, IUserServiceInjector {

    private UserAbstractDao userDao;
    private UserRoleAbstractDao userRoleDao;
    private String invalid_input = "Invalid Input";

    @Override
    public List<User> getAllUsers() throws SQLException, AwsSecretsManagerException {
        ArrayList<User> users = new ArrayList<>();
        users = userDao.getAll();
        
        users.removeIf(user -> {
			try {
				return user.getBannerId().equals("admin")
				        || userRoleDao.getRolesByBannerId(user.getBannerId()).contains("STUDENT")
				        || userRoleDao.getRolesByBannerId(user.getBannerId()).contains("TA");
			} catch (SQLException | AwsSecretsManagerException e) {
				 GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
				 return false;
			}
			
		});
        return users;
    }
    @Override
    public User getUser(int userId) throws SQLException, AwsSecretsManagerException {
    	return (User) userDao.get(userId);
    }

    @Override
    public void updateUserRole(int userId, String role) throws AllowedRolesNotSetException, SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(role) && ServiceUtility.isValidInt(userId)) {
            userRoleDao.updateRole(userId, role);
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalid_input);
        }
    }

    @Override
    public void injectUserDao(UserAbstractDao userDao) {
        if (ServiceUtility.isNotNull(userDao)) {
            this.userDao = userDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "user dao not injected");
        }

    }

    @Override
    public void injectUserRoleDao(UserRoleAbstractDao userRoleAbstractDao) {
        if (ServiceUtility.isNotNull(userRoleAbstractDao)) {
            this.userRoleDao = userRoleAbstractDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, " userRoleAbstractDao not injected");
        }

    }

    @Override
    public boolean validateBannerID(String bannerId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(bannerId)) {
            Set<String> roleSet = userRoleDao.getRolesByBannerId(bannerId);
            return roleSet.contains("STUDENT");
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "Bnaner id is null");
        }
        return false;
    }

}
