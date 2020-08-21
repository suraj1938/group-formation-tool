package com.app.group15.QuestionManager;

public class QuestionManagerServiceInjector {

    private QuestionManagerService questionManagerService;

    public QuestionManagerServiceInjector() {
        questionManagerService = new QuestionManagerService();
        questionManagerService.injectQuestionManagerInjectorService(new QuestionManagerDao());
    }

    public IQuestionManagerService getQuestionManagerService() {
        return questionManagerService;
    }

}
