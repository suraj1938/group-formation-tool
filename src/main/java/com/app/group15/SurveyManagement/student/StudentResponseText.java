package com.app.group15.SurveyManagement.student;

import com.app.group15.QuestionManager.Question;

public class StudentResponseText extends StudentResponse{
    private int id;
    
    private int surveyId;
    private String textResponse;
    private int studentId;
    
    private Question question;

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

    public String getTextResponse() {
        return textResponse;
    }

    public void setTextResponse(String textResponse) {
        this.textResponse = textResponse;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

	
    
    
}
