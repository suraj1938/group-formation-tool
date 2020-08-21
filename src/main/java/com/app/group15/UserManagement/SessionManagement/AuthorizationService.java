package com.app.group15.UserManagement.SessionManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.UserAbstractDao;
import com.app.group15.UserManagement.UserRoleAbstractDao;
import com.app.group15.UserManagement.UserRoleDao;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class AuthorizationService implements IAuthorizationService, IAuthorizationServiceInjector {

    private Set<String> allowedRoles = new HashSet<>();
    private String invalidInput = "Invalid input";
    UserRoleAbstractDao userRoleDao;

    @Override
    public Set<String> getAllowedRoles() {
        return allowedRoles;
    }

    @Override
    public void setAllowedRoles(String[] args) {
        if (ServiceUtility.isNotNull(args)) {
            this.allowedRoles.clear();
            this.allowedRoles.addAll(Arrays.asList(args));
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
    }

    @Override
    public boolean isAuthorized(HttpServletRequest request) throws SQLException, AwsSecretsManagerException {
        if (allowedRoles == null) {
            try {
                throw new AllowedRolesNotSetException("allowedRoles parameter not set");
            } catch (AllowedRolesNotSetException e) {
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        String bannerId = (String) request.getSession().getAttribute("BANNER_ID_SESSION");
        UserRoleDao userRoleDao = AppConfig.getInstance().getUserRoleDaoInjectorService().getUserRoleDao();
        Set<String> intersection = new HashSet<>(this.allowedRoles);
        Set<String> roles = userRoleDao.getRolesByBannerId(bannerId);
        intersection.retainAll(roles);
        return intersection.size() > 0;
    }

	@Override
	public void injectUserRoleDao(UserRoleAbstractDao userRoleDao) {
		if (ServiceUtility.isNotNull(userRoleDao)){
			this.userRoleDao=userRoleDao;
		} else {
			GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
		}
	}

}
