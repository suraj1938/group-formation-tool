package com.app.group15.SurveyManagement.student;

import com.app.group15.QuestionManager.Question;

public class StudentResponseNumeric extends StudentResponse {
    private int id;
   
    private int surveyId;
    private int numericResponse;
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

    public int getNumericResponse() {
        return numericResponse;
    }

    public void setNumericResponse(int numericResponse) {
        this.numericResponse = numericResponse;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

	
    
    
}
