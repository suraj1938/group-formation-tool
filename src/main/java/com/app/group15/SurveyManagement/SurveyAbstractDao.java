package com.app.group15.SurveyManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SurveyAbstractDao<T> implements IDao {
    @Override
    public Persistence get(int id) throws SQLException, AwsSecretsManagerException {
        return null;
    }

    @Override
    public List getAll() throws SQLException, AwsSecretsManagerException {
        return null;
    }

    @Override
    public int save(Persistence persistence) throws SQLException, AwsSecretsManagerException {
        return 0;
    }

    @Override
    public void update(Persistence persistence, int id) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public void delete(int id) throws SQLException, AwsSecretsManagerException {

    }

    public abstract Survey getSurvey(int id) throws SQLException, AwsSecretsManagerException;

    public abstract Survey getSurveyByCourseID(int courseID) throws SQLException, AwsSecretsManagerException;

    public abstract List getRuleByQuestionType(int questionType) throws SQLException, AwsSecretsManagerException;

    public abstract int saveSurvey(Survey survey) throws SQLException, AwsSecretsManagerException;

    public abstract void publishSurvey(Survey survey) throws SQLException, AwsSecretsManagerException;

    public abstract void unPublishSurvey(Survey survey) throws SQLException, AwsSecretsManagerException;

    public abstract int getRuleIdByRuleAndQuestionType(String rule, int questionType) throws SQLException, AwsSecretsManagerException;

    public abstract void saveNumericResponse(int surveyResponseQuestionId, int id, int questionId, int surveyId) throws SQLException, AwsSecretsManagerException;

    public abstract void saveTextResponse(int id, int questionId, String textResponse, int surveyId) throws SQLException, AwsSecretsManagerException;

    public abstract void saveChoiceResponse(int questionId, int surveyId, String choiceId, int id) throws SQLException, AwsSecretsManagerException;


    public abstract ArrayList<SurveyQuestionMapper> getQuestionsOfASurveySortedByOrder(int surveyId) throws SQLException, AwsSecretsManagerException;

    public abstract int getSurveyIdOfACourse(int courseId) throws SQLException, AwsSecretsManagerException;

    public abstract String getRuleFromId(int ruleId) throws SQLException, AwsSecretsManagerException;

    public abstract User getUser(int studentId) throws SQLException, AwsSecretsManagerException;

}

