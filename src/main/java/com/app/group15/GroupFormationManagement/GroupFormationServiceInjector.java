package com.app.group15.GroupFormationManagement;

import com.app.group15.UserManagement.UserServiceInjector;

public class GroupFormationServiceInjector {
	private GroupFormationService groupFormationService;
	
	public GroupFormationServiceInjector() {
		groupFormationService=new GroupFormationService();
		groupFormationService.injectGroupFormationAlgorithm(new MatrixGroupFormationAlgorithm());
		groupFormationService.injectUserService(new UserServiceInjector().getUserService());
	}

	public GroupFormationService getGroupFormationService() {
		return groupFormationService;
	}

	public void setGroupFormationService(GroupFormationService groupFormationService) {
		this.groupFormationService = groupFormationService;
	}
	
	
}
