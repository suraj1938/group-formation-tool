package com.app.group15.SurveyManagement.student;

import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.CourseManagement.Course;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SurveyStudentServiceTest {

    private ISurveyStudentService surveyStudentService = ServiceConfigForTest.getInstance().getSurveyStudentService();

    private SurveyFormResponse surveyFormResponse;
    private SurveyStudentDaoMock surveyStudentDaoMock = new SurveyStudentDaoMock();
    private User user;


    @Test
    public void submitResponse() throws SQLException, AwsSecretsManagerException {
        surveyFormResponse = surveyStudentDaoMock.getSurveyFrom();
        user = surveyStudentDaoMock.getUser();
        boolean submitted = surveyStudentService.submitResponse(user, surveyFormResponse);
        assertEquals(submitted, true);
    }


    @Test
    public void getSurveyQuestionWithOptionsTest() throws SQLException, AwsSecretsManagerException {

        int courseId = 10;
        SurveyFormResponse surveyQuestionWithOptions = surveyStudentService.getSurveyQuestionWithOptions(courseId);
        assertNotNull(surveyQuestionWithOptions);
        assertNotNull(surveyQuestionWithOptions.getSurveyId());
        assertNotNull(surveyQuestionWithOptions.getSurveyResponse());
        SurveyResponse surveyResponse = surveyQuestionWithOptions.getSurveyResponse().get(0);
        assertNotNull(surveyResponse);
    }


    @Test
    public void validateIfUserHasSubmittedSurveyBeforeTest() throws SQLException, AwsSecretsManagerException {

        user = surveyStudentDaoMock.getUser();
        List<Course> courseList = surveyStudentDaoMock.getCourseList();
        List<Boolean> appeared = surveyStudentService.validateIfUserHasSubmittedSurveyBefore(courseList, user.getId());
        assertEquals(appeared.get(0), true);
        assertNotNull(appeared);
    }
}
