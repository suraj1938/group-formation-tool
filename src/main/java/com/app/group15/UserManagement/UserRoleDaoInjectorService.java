package com.app.group15.UserManagement;

public class UserRoleDaoInjectorService {

	private UserRoleDao userRoleDao;

	public UserRoleDaoInjectorService() {
		userRoleDao = new UserRoleDao();
	}

	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

}
