package com.app.group15.QuestionManager;

import java.util.List;

import com.app.group15.Persistence.Persistence;

public class QuestionChoiceMapper extends Persistence{

    private int id;
    private int questionId;
    private List<Integer> questionChoiceIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<Integer> getQuestionChoiceId() {
        return questionChoiceIds;
    }

    public void setQuestionChoiceId(List<Integer> questionChoiceId) {
        this.questionChoiceIds = questionChoiceId;
    }


    @Override
    public String toString() {
        return "QuestionChoiceMapper{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", questionChoiceId=" + questionChoiceIds.toString() +
                '}';
    }
}
