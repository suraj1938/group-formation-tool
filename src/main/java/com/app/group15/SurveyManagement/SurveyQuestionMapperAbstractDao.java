package com.app.group15.SurveyManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;
import com.app.group15.QuestionManager.Question;

import java.sql.SQLException;
import java.util.List;

public abstract class SurveyQuestionMapperAbstractDao<T> implements IDao {
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

    public abstract int getHighestOrderValue(int surveyId) throws SQLException, AwsSecretsManagerException;

    public abstract int addQuestion(SurveyQuestionMapper surveyQuestionMapper) throws SQLException, AwsSecretsManagerException;

    public abstract List<Question> getAllSurveyQuestions(int surveyId) throws SQLException, AwsSecretsManagerException;

    public abstract void deleteSurveyQuestion(int questionId, int surveyId) throws SQLException, AwsSecretsManagerException;

    public abstract List<Question> getSurveyQuestionByInstructorID(int instructorID) throws SQLException, AwsSecretsManagerException;

    public abstract List<Question> getSurveyQuestionByCourseID(int course) throws SQLException, AwsSecretsManagerException;


    public abstract List<Question> getRemainingQuestionsForSurvey(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException;

}
