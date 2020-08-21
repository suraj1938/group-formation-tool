package com.app.group15.GroupFormationManagement;

import com.app.group15.QuestionManager.QuestionManagerServiceInjector;
import com.app.group15.SurveyManagement.SurveyServiceInjector;
import com.app.group15.SurveyManagement.student.SurveyStudentInjectorService;

public class GroupFormationAlgorithmServiceInjector {
	
	private GroupFormationAlgorithmService groupFormationService;
	
	public GroupFormationAlgorithmServiceInjector() {
		groupFormationService=new GroupFormationAlgorithmService();
		groupFormationService.injectSurveyService(new SurveyServiceInjector().getSurveyService());
		groupFormationService.injectSurveyStudentService(new SurveyStudentInjectorService().getSurveyStudentService());
		groupFormationService.injectQuestionService(new QuestionManagerServiceInjector().getQuestionManagerService());
	}

	public GroupFormationAlgorithmService getGroupFormationService() {
		return groupFormationService;
	}

	
}
