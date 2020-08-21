package com.app.group15.SurveyManagement;

import com.app.group15.QuestionManager.QuestionManagerAbstractDao;
import com.app.group15.SurveyManagement.student.SurveyStudentAbstractDao;

public interface ISurveyServiceInjector {

    void injectSurveyDao(SurveyAbstractDao surveyDao);

    void injectSurveyQuestionMapperDao(SurveyQuestionMapperAbstractDao surveyQuestionDao);

    void injectQuestionManagerDao(QuestionManagerAbstractDao questionManagerDao);

    void injectSurveyStudentDao(SurveyStudentAbstractDao surveyStudentDao);
}
