package com.app.group15.SurveyManagement;

public class SurveyDaoInjectorService {
	private SurveyDao surveyDao;

	public SurveyDaoInjectorService(){
		surveyDao = new SurveyDao();
		surveyDao.injectSurveyQuestionMapperDao(new SurveyQuestionMapperDao());
	}

	public SurveyDao getSurveyDao(){
		return surveyDao;
	}
}
