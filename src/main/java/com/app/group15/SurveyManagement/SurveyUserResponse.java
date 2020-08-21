package com.app.group15.SurveyManagement;

import com.app.group15.SurveyManagement.student.SurveyResponse;
import com.app.group15.UserManagement.User;

import java.util.List;

public class SurveyUserResponse {

    public List<SurveyResponse> surveyResponseList;
    public User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public List<SurveyResponse> getSurveyResponseList() {
        return surveyResponseList;
    }

    public void setSurveyResponseList(List<SurveyResponse> surveyResponseList) {
        this.surveyResponseList = surveyResponseList;
    }
}
