package com.app.group15.SurveyManagement;

import com.app.group15.Persistence.Persistence;
import com.app.group15.SurveyManagement.student.*;

public class SurveyManagementConcreteFactory implements ISurveyManagementAbstractFactory {

    private static ISurveyManagementAbstractFactory surveyManagementConcreteFactory;

    public static ISurveyManagementAbstractFactory getInstance() {
        if (null == SurveyManagementConcreteFactory.getUniqueInstance()) {
            surveyManagementConcreteFactory = new SurveyManagementConcreteFactory();
        }
        return SurveyManagementConcreteFactory.surveyManagementConcreteFactory;
    }

    private static ISurveyManagementAbstractFactory getUniqueInstance() {
        return surveyManagementConcreteFactory;
    }

    @Override
    public Persistence getSurveyModel() {
        return new Survey();
    }

    @Override
    public Persistence getSurveyQuestionMapperModel() {
        return new SurveyQuestionMapper();
    }

    @Override
    public StudentResponse getStudentResponseChoiceModel() {
        return new StudentResponseChoice();
    }

    @Override
    public StudentResponse getStudentResponseNumericModel() {
        return new StudentResponseNumeric();
    }

    @Override
    public StudentResponse getStudentResponseTextModel() {
        return new StudentResponseText();
    }

    @Override
    public SurveyFormResponse getSurveyFormResponseModel() {
        return new SurveyFormResponse();
    }

    @Override
    public SurveyResponse getSurveyResponseModel() {
        return new SurveyResponse();
    }

    @Override
    public SurveyUserResponse getSurveyUserResponse() {
        return new SurveyUserResponse();
    }

    @Override
    public SurveyQuestionMapperAbstractDao getSurveyQuestionMapperDao() {
        return new SurveyQuestionMapperDao();
    }

    @Override
    public SurveyAbstractDao getSurveyDao() {
        return new SurveyDaoInjectorService().getSurveyDao();
    }

    @Override
    public SurveyStudentAbstractDao getSurveyStudentDao() {
        return new SurveyStudentDao();
    }

    @Override
    public ISurveyService getSurveyService() {
        return new SurveyServiceInjector().getSurveyService();
    }

    @Override
    public ISurveyStudentService getSurveyStudentService() {
        return new SurveyStudentService();
    }


}
