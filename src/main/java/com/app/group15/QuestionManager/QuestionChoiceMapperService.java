package com.app.group15.QuestionManager;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

import java.sql.SQLException;

public class QuestionChoiceMapperService implements IQuestionChoiceMapperInjectorService, IQuestionChoiceMapperService {

    private QuestionChoiceMapperAbstractDao questionChoiceMapperDao;

    @Override
    public void injectQuestionChoiceMapperDao(QuestionChoiceMapperAbstractDao questionChoiceMapperDao) {
        this.questionChoiceMapperDao = questionChoiceMapperDao;
    }

    @Override
    public void deleteByQuestionId(int questionId) throws SQLException, AwsSecretsManagerException {
        questionChoiceMapperDao.deleteByQuestionId(questionId);
    }
}
