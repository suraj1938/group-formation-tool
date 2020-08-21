package com.app.group15.QuestionManager;

public class QuestionChoiceMapperServiceInjectorForTest {
    private QuestionChoiceMapperService questionChoiceMapperService;

    public QuestionChoiceMapperServiceInjectorForTest() {
        questionChoiceMapperService = new QuestionChoiceMapperService();
        questionChoiceMapperService.injectQuestionChoiceMapperDao(new QuestionChoiceMapperDaoMock());
    }

    public QuestionChoiceMapperService getQuestionChoiceMapperService() {
        return questionChoiceMapperService;
    }
}
