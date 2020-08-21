package com.app.group15.QuestionManager;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.IDao;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;
import java.util.List;

public abstract class QuestionManagerAbstractDao implements IDao {
    @Override
    public abstract Persistence get(int id) throws SQLException, AwsSecretsManagerException;

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public int save(Persistence persistence) {
        return 0;
    }

    @Override
    public void update(Persistence persistence, int id) {

    }

    @Override
    public void delete(int id) {

    }

    public abstract List<Question> getAllQuestionsOfInstructor(int instructorId) throws SQLException, AwsSecretsManagerException;

    public abstract List getAllQuestionTypes() throws SQLException, AwsSecretsManagerException;

    public abstract int saveQuestion(Question question) throws SQLException, AwsSecretsManagerException;

    public abstract int saveOption(Options option) throws SQLException, AwsSecretsManagerException;

    public abstract List<Options> getOptions(int questionId) throws SQLException, AwsSecretsManagerException;

    public abstract void saveQuestionOptionMapping(int insertedQuestionId, Integer insertedChoiceId) throws SQLException, AwsSecretsManagerException;
}
