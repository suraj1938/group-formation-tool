package com.app.group15.SurveyManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SurveyDaoMock extends SurveyAbstractDao {

    @Override
    public Survey getSurvey(int id) throws SQLException, AwsSecretsManagerException {
        Survey survey = new Survey();
        survey.setId(1);
        survey.setPublishState(1);
        survey.setCourseId(15);
        return survey;
    }

    @Override
    public Survey getSurveyByCourseID(int courseID) throws SQLException, AwsSecretsManagerException {
        Survey survey = new Survey();
        survey.setId(1);
        survey.setPublishState(1);
        survey.setCourseId(courseID);
        return survey;
    }

    @Override
    public List getRuleByQuestionType(int questionType) throws SQLException, AwsSecretsManagerException {
        List<String> ruleList;
        if (questionType == 1) {
            ruleList = new ArrayList<String>(Arrays.asList("SIMILAR", "DISSIMILAR", "GREATER", "LESS"));
        } else {
            ruleList = new ArrayList<String>(Arrays.asList("SIMILAR", "DISSIMILAR"));
        }
        return ruleList;
    }

    @Override
    public int saveSurvey(Survey survey) throws SQLException, AwsSecretsManagerException {
        return 1;
    }

    @Override
    public void publishSurvey(Survey survey) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public void unPublishSurvey(Survey survey) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public int getRuleIdByRuleAndQuestionType(String rule, int questionType) throws SQLException, AwsSecretsManagerException {
        return 1;
    }

    @Override
    public void saveNumericResponse(int surveyResponseQuestionId, int id, int questionId, int surveyId) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public void saveTextResponse(int id, int questionId, String textResponse, int surveyId) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public void saveChoiceResponse(int questionId, int surveyId, String choiceId, int id) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public ArrayList<SurveyQuestionMapper> getQuestionsOfASurveySortedByOrder(int surveyId) throws SQLException, AwsSecretsManagerException {
        return null;
    }

    @Override
    public int getSurveyIdOfACourse(int courseId) throws SQLException, AwsSecretsManagerException {
        return 0;
    }

    @Override
    public String getRuleFromId(int ruleId) throws SQLException, AwsSecretsManagerException {
        return null;
    }

    @Override
    public User getUser(int studentId) throws SQLException, AwsSecretsManagerException {
        return null;
    }
}

