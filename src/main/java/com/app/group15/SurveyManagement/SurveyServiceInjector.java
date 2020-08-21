package com.app.group15.SurveyManagement;

import com.app.group15.QuestionManager.QuestionManagerDao;
import com.app.group15.SurveyManagement.student.SurveyStudentDao;

public class SurveyServiceInjector {
    private SurveyService surveyService;

    public SurveyServiceInjector() {
        surveyService = new SurveyService();
        surveyService.injectSurveyDao(new SurveyDao());
        surveyService.injectSurveyQuestionMapperDao(new SurveyQuestionMapperDao());
        surveyService.injectQuestionManagerDao(new QuestionManagerDao());
        surveyService.injectSurveyStudentDao(new SurveyStudentDao());
    }

    public SurveyService getSurveyService() {
        return surveyService;
    }

}
