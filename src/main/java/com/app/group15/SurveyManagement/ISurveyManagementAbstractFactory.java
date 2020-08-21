package com.app.group15.SurveyManagement;

import com.app.group15.Persistence.Persistence;
import com.app.group15.SurveyManagement.student.*;

public interface ISurveyManagementAbstractFactory {

    public Persistence getSurveyModel();

    public Persistence getSurveyQuestionMapperModel();

    public StudentResponse getStudentResponseChoiceModel();

    public StudentResponse getStudentResponseNumericModel();

    public StudentResponse getStudentResponseTextModel();

    public SurveyFormResponse getSurveyFormResponseModel();

    public SurveyResponse getSurveyResponseModel();

    public SurveyUserResponse getSurveyUserResponse();

    public SurveyQuestionMapperAbstractDao getSurveyQuestionMapperDao();

    public SurveyAbstractDao getSurveyDao();

    public SurveyStudentAbstractDao getSurveyStudentDao();

    public ISurveyService getSurveyService();

    public ISurveyStudentService getSurveyStudentService();

}
