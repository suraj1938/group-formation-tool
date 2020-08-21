package com.app.group15.UserManagement;

import com.app.group15.Config.AppConfig;

public class UserDaoInjectorService {

	private UserDao userDao;

	public UserDaoInjectorService() {

		userDao = new UserDao();
		userDao.injectUserRoleDao(new UserRoleDaoInjectorService().getUserRoleDao());
	}

	public UserDao getUserDao() {
		return userDao;
	}

}
