package com.app.group15.SurveyManagement.student;

import com.app.group15.Config.AppConfig;
import com.app.group15.CourseManagement.Course;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.QuestionManager.Options;
import com.app.group15.QuestionManager.Question;
import com.app.group15.UserManagement.IUserManagementAbstractFactory;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyStudentDaoMock extends SurveyStudentAbstractDao {

	private IUserManagementAbstractFactory userManagementAbstractFactory=AppConfig.getInstance().getUserManagementAbstractFactory();

    @Override
    public void saveNumericResponse(int questionId, int surveyId, int numericResponse, int userId) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public void saveTextResponse(int questionId, int surveyId, String textResponse, int userId) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public void saveChoiceResponse(int questionId, int surveyId, String choiceId, int userId) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public List<Question> getSurveyQuestionWithCourseByCourseID(int courseID) throws SQLException, AwsSecretsManagerException {
        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestionTypeId(1);
        question.setSurveyId(1);
        question.setQuestionTitle("Title");
        question.setQuestionText("Text");
        question.setQuestionInstructorId(1);
        question.setQuestionAddedDate("2020-08-10");
        Options options = new Options();
        List<Question> questionList;
        List<Options> optionList = new ArrayList<>();
        optionList.add(options);
        question.setOptions(optionList);
        questionList = new ArrayList<>();
        questionList.add(question);
        return questionList;
    }

    @Override
    public List<Options> getQuestionOptionsByQuestionId(int questionId) throws SQLException, AwsSecretsManagerException {
        return null;
    }

    @Override
    public List<Integer> validateUserHasAppearedSurveyBefore(int userId, int surveyId) throws SQLException, AwsSecretsManagerException {
        List<Integer> appeared = new ArrayList<>();
        appeared.add(0);
        appeared.add(0);
        return appeared;
    }

    @Override
    public List<StudentResponseNumeric> getNumericStudentResponsesOfASurvey(int surveyId) throws SQLException, AwsSecretsManagerException {
        return null;
    }

    @Override
    public List<StudentResponseChoice> getChoiceStudentResponsesOfASurvey(int surveyId) throws SQLException, AwsSecretsManagerException {
        return null;
    }

    @Override
    public List<StudentResponseText> getTextStudentResponsesOfASurvey(int surveyId) throws SQLException, AwsSecretsManagerException {
        return null;
    }

    @Override
    public String getChoiceValue(int choiceId) throws SQLException, AwsSecretsManagerException {
        return null;
    }

    public SurveyFormResponse getSurveyFrom() {
        SurveyFormResponse surveyFormResponse = new SurveyFormResponse();
        surveyFormResponse.setSurveyId(1);
        List<SurveyResponse> surveyResponseList = new ArrayList<>();
        SurveyResponse surveyResponseQstNumeric = new SurveyResponse();
        surveyResponseQstNumeric.setQuestionId(1);
        surveyResponseQstNumeric.setQuestionText("Numeric Question");
        surveyResponseQstNumeric.setQuestionTitle("Question");
        surveyResponseQstNumeric.setNumericResponse(1);
        surveyResponseQstNumeric.setQuestionInstructorId(1);
        surveyResponseQstNumeric.setQuestionTypeId(1);
        surveyResponseList.add(surveyResponseQstNumeric);

        SurveyResponse surveyResponseQstText = new SurveyResponse();
        surveyResponseQstText.setQuestionId(2);
        surveyResponseQstText.setQuestionText("Text Question");
        surveyResponseQstText.setQuestionTitle("Question");
        surveyResponseQstText.setTextResponse("Text response");
        surveyResponseQstText.setQuestionInstructorId(1);
        surveyResponseQstText.setQuestionTypeId(4);
        surveyResponseList.add(surveyResponseQstText);


        SurveyResponse surveyResponseQstMCQSingleChoice = new SurveyResponse();
        surveyResponseQstMCQSingleChoice.setQuestionId(3);
        surveyResponseQstMCQSingleChoice.setQuestionText("MCQ SC Question");
        surveyResponseQstMCQSingleChoice.setQuestionTitle("Question");
        surveyResponseQstMCQSingleChoice.setChoiceId("66");
        surveyResponseQstMCQSingleChoice.setQuestionInstructorId(1);
        surveyResponseQstMCQSingleChoice.setQuestionTypeId(2);
        surveyResponseList.add(surveyResponseQstMCQSingleChoice);

        SurveyResponse surveyResponseQstMCQMultipleChoice = new SurveyResponse();
        surveyResponseQstMCQMultipleChoice.setQuestionId(4);
        surveyResponseQstMCQMultipleChoice.setQuestionText("MCQ MC Question");
        surveyResponseQstMCQMultipleChoice.setQuestionTitle("Question");
        surveyResponseQstMCQMultipleChoice.setChoiceId("66,67");
        surveyResponseQstMCQMultipleChoice.setQuestionInstructorId(1);
        surveyResponseQstMCQMultipleChoice.setQuestionTypeId(3);
        surveyResponseList.add(surveyResponseQstMCQMultipleChoice);

        surveyFormResponse.setSurveyResponse(surveyResponseList);
        return surveyFormResponse;
    }

    public List<Course> getCourseList() {
        Course course = new Course();
        course.setName("Course 1");
        course.setId(1);
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);

        return courseList;
    }

    public User getUser() {

        User user = (User) userManagementAbstractFactory.getUserModel();
        user.setBannerId("B00838074");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setId(1);
        return user;
    }
}
