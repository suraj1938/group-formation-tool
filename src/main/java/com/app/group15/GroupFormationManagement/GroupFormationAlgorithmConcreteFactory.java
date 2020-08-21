package com.app.group15.GroupFormationManagement;



public class GroupFormationAlgorithmConcreteFactory implements IGroupFormationAlgorithmAbstractFactory {

	private static GroupFormationAlgorithmConcreteFactory concreteFactory;

	public static GroupFormationAlgorithmConcreteFactory getInstance() {
		if (null == GroupFormationAlgorithmConcreteFactory.getUniqueInstance()) {
			concreteFactory = new GroupFormationAlgorithmConcreteFactory();
		}
		return GroupFormationAlgorithmConcreteFactory.concreteFactory;
	}

	private static IGroupFormationAlgorithmAbstractFactory getUniqueInstance() {
		return concreteFactory;
	}

	@Override
	public IGroupFormationAlgorithmService getGroupFormationAlgorithmService() {
		return new GroupFormationAlgorithmServiceInjector().getGroupFormationService();
	}

	@Override
	public IGroupFormationService getGroupFormationService() {
		return new GroupFormationServiceInjector().getGroupFormationService();
	}

	@Override
	public GroupFormationAlgorithm getGroupFormationAlgorithm() {
		// TODO Auto-generated method stub
		return new MatrixGroupFormationAlgorithm();
	}

}
