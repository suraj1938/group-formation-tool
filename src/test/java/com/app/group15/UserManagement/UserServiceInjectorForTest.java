package com.app.group15.UserManagement;

public class UserServiceInjectorForTest {

	 private UserService userService;
	 
	 public UserServiceInjectorForTest() {
	        userService = new UserService();
	        userService.injectUserDao(new UserDaoMock());
	        userService.injectUserRoleDao(new UserRoleDaoMock());
	    }

	    public UserService getUserService() {
	        return userService;
	    }
}
