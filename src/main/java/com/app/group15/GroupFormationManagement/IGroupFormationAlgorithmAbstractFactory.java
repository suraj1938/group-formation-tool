package com.app.group15.GroupFormationManagement;

public interface IGroupFormationAlgorithmAbstractFactory {
	
	public IGroupFormationAlgorithmService getGroupFormationAlgorithmService();
	public IGroupFormationService getGroupFormationService();
	public GroupFormationAlgorithm getGroupFormationAlgorithm();
}
