package com.app.group15.UserManagement;


import com.app.group15.Persistence.IDao;

public interface IUserRoleDaoInjector {

    void injectUserRoleDao(@SuppressWarnings("rawtypes") IDao userRoleDao);
}
