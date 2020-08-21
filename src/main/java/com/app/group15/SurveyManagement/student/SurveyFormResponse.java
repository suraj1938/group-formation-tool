package com.app.group15.SurveyManagement.student;

import java.util.List;

public class SurveyFormResponse {


    public List<SurveyResponse> surveyResponse;
    public int surveyId;

    public List<SurveyResponse> getSurveyResponse() {
        return surveyResponse;
    }

    public void setSurveyResponse(List<SurveyResponse> surveyResponse) {
        this.surveyResponse = surveyResponse;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }
}
