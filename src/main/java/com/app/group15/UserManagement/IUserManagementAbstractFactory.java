package com.app.group15.UserManagement;

import com.app.group15.Persistence.Persistence;

public interface IUserManagementAbstractFactory {
	Persistence getUserModel();

	Persistence getUserRoleModel();

	Persistence getUserRoleMapperModel();

	UserAbstractDao getUserDao();

	UserRoleAbstractDao getUserRoleDao();

	IUserService getUserService();
}
