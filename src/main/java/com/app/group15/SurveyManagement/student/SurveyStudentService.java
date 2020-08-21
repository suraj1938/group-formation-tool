package com.app.group15.SurveyManagement.student;

import com.app.group15.Config.AppConfig;
import com.app.group15.CourseManagement.Course;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.QuestionManager.Question;
import com.app.group15.SurveyManagement.ISurveyManagementAbstractFactory;
import com.app.group15.SurveyManagement.Survey;
import com.app.group15.SurveyManagement.SurveyAbstractDao;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyStudentService implements ISurveyStudentService, ISurveyStudentServiceInjector {

    private ISurveyManagementAbstractFactory surveyManagementAbstractFactory = AppConfig.getInstance().getSurveyManagementAbstractFactory();
    private SurveyStudentAbstractDao surveyStudentDao=surveyManagementAbstractFactory.getSurveyStudentDao();
    private SurveyAbstractDao surveyDao=surveyManagementAbstractFactory.getSurveyDao();

    @Override
    public boolean submitResponse(User user, SurveyFormResponse surveyFormResponse) throws SQLException, AwsSecretsManagerException {

        for (SurveyResponse surveyResponse : surveyFormResponse.getSurveyResponse()) {
            if (surveyResponse.getQuestionTypeId() == 1) {
                surveyStudentDao.saveNumericResponse(surveyResponse.getQuestionId(), surveyFormResponse.getSurveyId(), surveyResponse.getNumericResponse(), user.getId());
            } else if (surveyResponse.getQuestionTypeId() == 2 && Integer.parseInt(surveyResponse.getChoiceId()) > 0) {
                surveyStudentDao.saveChoiceResponse(surveyResponse.getQuestionId(), surveyFormResponse.getSurveyId(), surveyResponse.getChoiceId(), user.getId());
            } else if (surveyResponse.getQuestionTypeId() == 3 && ServiceUtility.isNotNull(surveyResponse.getChoiceId())) {
                String[] choices = surveyResponse.getChoiceId().split(",");
                for (String choice : choices) {
                    int chose = Integer.parseInt(choice);
                    if (chose > 0) {
                        surveyStudentDao.saveChoiceResponse(surveyResponse.getQuestionId(), surveyFormResponse.getSurveyId(), choice, user.getId());
                    }
                }
            } else if (surveyResponse.getQuestionTypeId() == 4) {
                surveyStudentDao.saveTextResponse(surveyResponse.getQuestionId(), surveyFormResponse.getSurveyId(), surveyResponse.getTextResponse(), user.getId());
            }
        }
        return true;
    }

    @Override
    public SurveyFormResponse getSurveyQuestionWithOptions(int courseId) throws SQLException, AwsSecretsManagerException {
        List<Question> questionList = surveyStudentDao.getSurveyQuestionWithCourseByCourseID(courseId);
        List<SurveyResponse> surveyResponse = new ArrayList<>();
        int surveyId = questionList.get(0).getSurveyId();
        for (Question qst : questionList) {
            SurveyResponse response = surveyManagementAbstractFactory.getSurveyResponseModel();
            response.setQuestionId(qst.getQuestionId());
            response.setQuestionTitle(qst.getQuestionTitle());
            response.setQuestionTypeId(qst.getQuestionTypeId());
            response.setQuestionInstructorId(qst.getQuestionInstructorId());
            response.setQuestionText(qst.getQuestionText());
            response.setOptions(qst.getOptions());
            response.setQuestionAddedDate(qst.getQuestionAddedDate());
            surveyResponse.add(response);
        }
        SurveyFormResponse surveyFormResponse = surveyManagementAbstractFactory.getSurveyFormResponseModel();
        surveyFormResponse.setSurveyId(surveyId);
        surveyFormResponse.setSurveyResponse(surveyResponse);
        return surveyFormResponse;
    }


    @Override
    public List<Boolean> validateIfUserHasSubmittedSurveyBefore(List<Course> courseList, int userId) throws SQLException, AwsSecretsManagerException {
        List<Survey> surveyList = new ArrayList<>();
        for (Course course : courseList) {
            Survey survey = surveyDao.getSurveyByCourseID(course.getId());
            surveyList.add(survey);
        }
        List<Boolean> appearedList = new ArrayList<>();
        for (Survey survey : surveyList) {
            List<Integer> columnCount = surveyStudentDao.validateUserHasAppearedSurveyBefore(userId, survey.getId());
            int colCount = 0;
            for (Integer count : columnCount) {
                colCount += count;
            }

            if (colCount > 0) {
                appearedList.add(false);
            } else {
                if (survey.getPublishState() == 1) {
                    appearedList.add(true);
                } else {
                    appearedList.add(false);
                }
            }
        }
        return appearedList;
    }

    @Override
    public void injectSurveyStudentDao(SurveyStudentAbstractDao surveyStudentAbstractDao) {
        this.surveyStudentDao = surveyStudentAbstractDao;
    }


    @Override
    public void injectSurveyDao(SurveyAbstractDao surveyAbstractDao) {
        this.surveyDao = surveyAbstractDao;
    }

    @Override
    public List<StudentResponseNumeric> getNumericStudentResponsesForASurvey(int surveyId) throws SQLException, AwsSecretsManagerException {
        return this.surveyStudentDao.getNumericStudentResponsesOfASurvey(surveyId);
    }

    @Override
    public List<StudentResponseText> getTextStudentResponsesForASurvey(int surveyId) throws SQLException, AwsSecretsManagerException {
        return this.surveyStudentDao.getTextStudentResponsesOfASurvey(surveyId);
    }

    @Override
    public List<StudentResponseChoice> getChoiceStudentResponsesForASurvey(int surveyId) throws SQLException, AwsSecretsManagerException {
        return this.surveyStudentDao.getChoiceStudentResponsesOfASurvey(surveyId);
    }

}
