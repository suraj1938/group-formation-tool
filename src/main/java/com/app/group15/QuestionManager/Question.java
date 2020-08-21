package com.app.group15.QuestionManager;

import com.app.group15.Persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class Question extends Persistence {

    private int questionId;
    private String questionTitle;
    private int questionTypeId;
    private int questionInstructorId;
    private String questionText;
    private String questionAddedDate;
    private List<Options> options;
    private int surveyId;

    public Question() {
        options = new ArrayList<>();
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public void addOptions(Options options) {
        this.options.add(options);
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public int getQuestionInstructorId() {
        return questionInstructorId;
    }

    public void setQuestionInstructorId(int questionInstructorId) {
        this.questionInstructorId = questionInstructorId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionAddedDate() {
        return questionAddedDate;
    }

    public void setQuestionAddedDate(String questionAddedDate) {
        this.questionAddedDate = questionAddedDate;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionTitle='" + questionTitle + '\'' +
                ", questionTypeId=" + questionTypeId +
                ", questionInstructorId=" + questionInstructorId +
                ", questionText='" + questionText + '\'' +
                ", questionAddedDate=" + questionAddedDate +
                '}';
    }
}
