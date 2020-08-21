package com.app.group15.UserManagement.SessionManagement;

public interface ISessionManagementAbstractFactory {
	IAuthorizationService getAuthorizationService();

	ISessionService getSessionService();
}
