package com.app.group15.SurveyManagement.student;

import com.app.group15.SurveyManagement.SurveyDao;

public class SurveyStudentInjectorService {
    private SurveyStudentService surveyStudentService;

    public SurveyStudentInjectorService() {
        surveyStudentService = new SurveyStudentService();
        surveyStudentService.injectSurveyStudentDao(new SurveyStudentDao());
        surveyStudentService.injectSurveyDao(new SurveyDao());
    }

    public SurveyStudentService getSurveyStudentService() {
        return surveyStudentService;
    }
}
