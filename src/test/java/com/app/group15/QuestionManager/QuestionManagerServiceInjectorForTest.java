package com.app.group15.QuestionManager;

public class QuestionManagerServiceInjectorForTest {
    private QuestionManagerService questionManagerService;

    public QuestionManagerServiceInjectorForTest() {
        questionManagerService = new QuestionManagerService();
        questionManagerService.injectQuestionManagerInjectorService(new QuestionManagerDaoMock());
    }

    public QuestionManagerService getQuestionManagerService() {
        return questionManagerService;
    }

}
