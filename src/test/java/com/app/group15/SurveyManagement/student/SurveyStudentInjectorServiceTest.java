package com.app.group15.SurveyManagement.student;

import com.app.group15.SurveyManagement.SurveyDaoMock;

public class SurveyStudentInjectorServiceTest {


    private SurveyStudentService surveyStudentService;

    public SurveyStudentInjectorServiceTest() {
        surveyStudentService = new SurveyStudentService();
        surveyStudentService.injectSurveyDao(new SurveyDaoMock());
        surveyStudentService.injectSurveyStudentDao(new SurveyStudentDaoMock());
    }

    public SurveyStudentService getSurveyStudentService() {
        return surveyStudentService;
    }

}
