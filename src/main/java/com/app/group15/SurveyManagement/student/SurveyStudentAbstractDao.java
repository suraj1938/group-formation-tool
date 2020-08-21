package com.app.group15.SurveyManagement.student;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;
import com.app.group15.QuestionManager.Options;
import com.app.group15.QuestionManager.Question;

import java.sql.SQLException;
import java.util.List;

public abstract class SurveyStudentAbstractDao implements IDao {
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

    public abstract void saveNumericResponse(int questionId, int surveyId, int numericResponse, int userId) throws SQLException, AwsSecretsManagerException;

    public abstract void saveTextResponse(int questionId, int surveyId, String textResponse, int userId) throws SQLException, AwsSecretsManagerException;

    public abstract void saveChoiceResponse(int questionId, int surveyId, String choiceId, int userId) throws SQLException, AwsSecretsManagerException;

    public abstract List<Question> getSurveyQuestionWithCourseByCourseID(int courseID) throws SQLException, AwsSecretsManagerException;

    public abstract List<Options> getQuestionOptionsByQuestionId(int questionId) throws SQLException, AwsSecretsManagerException;


    public abstract List<Integer> validateUserHasAppearedSurveyBefore(int userId, int surveyId) throws SQLException, AwsSecretsManagerException;


    public abstract List<StudentResponseNumeric> getNumericStudentResponsesOfASurvey(int surveyId) throws SQLException, AwsSecretsManagerException;

    public abstract List<StudentResponseChoice> getChoiceStudentResponsesOfASurvey(int surveyId) throws SQLException, AwsSecretsManagerException;

    public abstract List<StudentResponseText> getTextStudentResponsesOfASurvey(int surveyId) throws SQLException, AwsSecretsManagerException;

    public abstract String getChoiceValue(int choiceId) throws SQLException, AwsSecretsManagerException;
}
