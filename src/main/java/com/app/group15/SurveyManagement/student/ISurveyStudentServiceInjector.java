package com.app.group15.SurveyManagement.student;

import com.app.group15.SurveyManagement.SurveyAbstractDao;

public interface ISurveyStudentServiceInjector {

    void injectSurveyStudentDao(SurveyStudentAbstractDao surveyStudentAbstractDao);

    void injectSurveyDao(SurveyAbstractDao surveyAbstractDao);
}
