package com.app.group15.UserManagement;

public class UserServiceInjector {

    private UserService userService;

    public UserServiceInjector() {
        userService = new UserService();
        userService.injectUserDao(new UserDaoInjectorService().getUserDao());
        userService.injectUserRoleDao(new UserRoleDaoInjectorService().getUserRoleDao());
    }

    public UserService getUserService() {
        return userService;
    }


}
