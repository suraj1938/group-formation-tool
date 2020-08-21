package com.app.group15.UserManagement.SessionManagement;

public class SessionManagementConcreteFactory implements ISessionManagementAbstractFactory{

	private static ISessionManagementAbstractFactory sessionManagementConcreteFactory;

	public static ISessionManagementAbstractFactory getInstance(){
		if (null == SessionManagementConcreteFactory.getUniqueInstance()) {
			sessionManagementConcreteFactory=new SessionManagementConcreteFactory();
		}
		return SessionManagementConcreteFactory.sessionManagementConcreteFactory;
	}

	public static ISessionManagementAbstractFactory getUniqueInstance(){
		return sessionManagementConcreteFactory;
	}

	@Override
	public IAuthorizationService getAuthorizationService() {
		return new AuthorizationServiceInjector().getAuthorizationService();
	}

	@Override
	public ISessionService getSessionService() {
		return new SessionService();
	}
}
