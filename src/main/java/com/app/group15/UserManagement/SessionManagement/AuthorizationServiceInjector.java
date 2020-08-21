package com.app.group15.UserManagement.SessionManagement;

import com.app.group15.UserManagement.UserRoleDaoInjectorService;

public class AuthorizationServiceInjector {

	private AuthorizationService authorizationService;

	public AuthorizationServiceInjector(){
		authorizationService=new AuthorizationService();
		authorizationService.injectUserRoleDao(new UserRoleDaoInjectorService().getUserRoleDao());
	}

	public AuthorizationService getAuthorizationService(){
		return authorizationService;
	}
}
