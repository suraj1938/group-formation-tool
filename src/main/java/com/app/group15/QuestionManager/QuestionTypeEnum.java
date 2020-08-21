package com.app.group15.QuestionManager;

public enum QuestionTypeEnum {

    NUMERIC("Numeric"),
    MCQ_SC("Multiple choice, Choose one"),
    MCQ_MC("Multiple Choice, Choose multiple"),
    TEXT("Free text");

    private final String questionType;

    QuestionTypeEnum(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionType() {
        return questionType;
    }
}
