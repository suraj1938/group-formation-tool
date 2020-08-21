package com.app.group15.UserManagement;

public interface IUserServiceInjector {
    void injectUserDao(UserAbstractDao userDao);

    void injectUserRoleDao(UserRoleAbstractDao userRoleAbstractDao);
}
