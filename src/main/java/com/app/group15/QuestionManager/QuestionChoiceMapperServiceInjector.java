package com.app.group15.QuestionManager;

public class QuestionChoiceMapperServiceInjector {
    private QuestionChoiceMapperService questionChoiceMapperService;

    public QuestionChoiceMapperServiceInjector() {
        questionChoiceMapperService = new QuestionChoiceMapperService();
        questionChoiceMapperService.injectQuestionChoiceMapperDao(new QuestionChoiceMapperDao());
    }

    public IQuestionChoiceMapperService getQuestionChoiceMapperService() {
        return questionChoiceMapperService;
    }
}
