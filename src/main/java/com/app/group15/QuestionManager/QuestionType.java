package com.app.group15.QuestionManager;

import com.app.group15.Persistence.Persistence;

public class QuestionType extends Persistence{

    private int id;
    private String questionType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }


    @Override
    public String toString() {
        return "QuestionType{" +
                "id=" + id +
                ", questionType='" + questionType + '\'' +
                '}';
    }
}
