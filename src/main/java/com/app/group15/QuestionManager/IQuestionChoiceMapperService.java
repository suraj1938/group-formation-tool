package com.app.group15.QuestionManager;

import java.sql.SQLException;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public interface IQuestionChoiceMapperService {
    void deleteByQuestionId(int questionId) throws SQLException, AwsSecretsManagerException;
}
