package com.app.group15.QuestionManager;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import java.sql.SQLException;

class QuestionChoiceMapperDaoMock extends QuestionChoiceMapperAbstractDao {


    @Override
    public void deleteByQuestionId(int questionId) throws SQLException, AwsSecretsManagerException {

    }

    public int deleteByQuestionIdMock(int questionId) throws SQLException, AwsSecretsManagerException {
        return questionId;
    }
}
