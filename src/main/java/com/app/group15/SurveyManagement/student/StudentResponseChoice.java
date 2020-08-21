package com.app.group15.SurveyManagement.student;

import com.app.group15.QuestionManager.Question;

public class StudentResponseChoice extends StudentResponse {
    private int id;

    private int surveyId;
    private int choiceId;
    private int studentId;
    private Integer cumulativeValue;
    private Question question;
    private String choiceText;

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Integer getCumulativeValue() {
        return cumulativeValue;
    }

    public void setCumulativeValue(Integer cumulativeValue) {
        this.cumulativeValue = cumulativeValue;
    }


}
