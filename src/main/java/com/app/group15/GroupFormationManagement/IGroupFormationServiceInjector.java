package com.app.group15.GroupFormationManagement;

import com.app.group15.UserManagement.IUserService;

public interface IGroupFormationServiceInjector {
	
	public void injectGroupFormationAlgorithm(GroupFormationAlgorithm groupFormationAlgorithm);
	public void injectUserService(IUserService userService);
}
