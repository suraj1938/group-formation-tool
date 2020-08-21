package com.app.group15.SurveyManagement;

import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.QuestionManager.Question;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SurveyServiceTest {
    private SurveyDaoMock surveyDaoMock = new SurveyDaoMock();
    private ISurveyService surveyService = ServiceConfigForTest.getInstance().getSurveyService();

    @Test
    void getSurveyQuestionByInstructorID() throws SQLException, AwsSecretsManagerException {
        List<Question> questionList = surveyService.getSurveyQuestionByInstructorID(1);
        int i = 0;
        for (i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            assertEquals(question.getQuestionId(), i);
        }
    }

    @Test
    void getSurveyByCourseId() throws SQLException, AwsSecretsManagerException {
        Survey survey = surveyService.getSurveyByCourseId(1);
        assertEquals(1, survey.getId());
    }

    @Test
    void getSurveyQuestionByCourseID() throws SQLException, AwsSecretsManagerException {
        List<Question> questionList = surveyService.getSurveyQuestionByCourseID(1);
        int i = 0;
        for (i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            assertEquals(question.getQuestionId(), i);
        }
    }

    @Test
    void getRemainingQuestionsForSurvey() {
//		List<Question> questionList=surveyService.getRemainingQuestionsForSurvey(1);
//		int i=0;
//		for(i=0;i<questionList.size();i++){
//			Question question = questionList.get(i);
//			assertEquals(question.getQuestionId(), i);
//		}
    }

    @Test
    void createSurveyIfNotExists() throws SQLException, AwsSecretsManagerException {
        Survey survey = null;
        assertNull(survey);

        survey = surveyService.getSurveyByCourseId(1);
        assertNotNull(survey);
    }

    @Test
    void addQuestionToSurvey() throws SQLException, AwsSecretsManagerException {
        int result = surveyService.addQuestionToSurvey(15, 1, "SIMILAR", 1);
        assertEquals(result, 1);
    }

    @Test
    void deleteSurveyQuestion() throws SQLException, AwsSecretsManagerException {
        surveyService.deleteSurveyQuestion(1, 1);
    }

    @Test
    void publishSurvey() throws SQLException, AwsSecretsManagerException {
        Survey survey = surveyDaoMock.getSurveyByCourseID(15);
        surveyDaoMock.publishSurvey(survey);
    }

    @Test
    void unPublishSurvey() throws SQLException, AwsSecretsManagerException {
        Survey survey = surveyDaoMock.getSurveyByCourseID(15);
        surveyDaoMock.unPublishSurvey(survey);
    }

    @Test
    void injectSurveyDao() {
    }

    @Test
    void injectSurveyQuestionMapperDao() {
    }

    @Test
    void injectQuestionManagerDao() {
    }
}
