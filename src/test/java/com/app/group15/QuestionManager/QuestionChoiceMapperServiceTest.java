package com.app.group15.QuestionManager;

import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionChoiceMapperServiceTest {
    private QuestionChoiceMapperDaoMock questionChoiceMapperDaoMock = new QuestionChoiceMapperDaoMock();
    private IQuestionChoiceMapperService questionChoiceMapperService = ServiceConfigForTest.getInstance().getQuestionChoiceMapperService();

    @Test
    void deleteByQuestionIdTest() throws SQLException, AwsSecretsManagerException {
        int resp = questionChoiceMapperDaoMock.deleteByQuestionIdMock(1);
        questionChoiceMapperDaoMock.deleteByQuestionId(1);
        assertEquals(resp, 1);
    }
}
