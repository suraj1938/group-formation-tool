package com.app.group15.GroupFormationManagement;

import com.app.group15.QuestionManager.IQuestionManagerService;
import com.app.group15.SurveyManagement.ISurveyService;
import com.app.group15.SurveyManagement.student.ISurveyStudentService;

public interface IGroupFormationAlgorithmServiceInjector {
	public void injectSurveyService(ISurveyService surveyService);
	public void injectSurveyStudentService(ISurveyStudentService surveyStudentService);
	public void injectQuestionService(IQuestionManagerService questionService);

}
