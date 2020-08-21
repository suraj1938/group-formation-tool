package com.app.group15.SurveyManagement;

import com.app.group15.QuestionManager.QuestionManagerDao;
import com.app.group15.QuestionManager.QuestionManagerDaoMock;

public class SurveyServiceInjectorForTest {
	private SurveyService surveyService;

	public SurveyServiceInjectorForTest() {
		surveyService = new SurveyService();
		surveyService.injectSurveyDao(new SurveyDaoMock());
		surveyService.injectSurveyQuestionMapperDao(new SurveyQuestionMapperDaoMock());
		surveyService.injectQuestionManagerDao(new QuestionManagerDaoMock());
	}

	public SurveyService getSurveyService() {
		return surveyService;
	}
}
