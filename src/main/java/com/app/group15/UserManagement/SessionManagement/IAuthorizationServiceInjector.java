package com.app.group15.UserManagement.SessionManagement;

import com.app.group15.UserManagement.UserRoleAbstractDao;

public interface IAuthorizationServiceInjector {
	void injectUserRoleDao(UserRoleAbstractDao userRoleDao);
}
