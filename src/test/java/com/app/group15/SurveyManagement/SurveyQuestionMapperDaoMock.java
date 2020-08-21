package com.app.group15.SurveyManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.QuestionManager.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyQuestionMapperDaoMock extends SurveyQuestionMapperAbstractDao {
	@Override
	public int getHighestOrderValue(int surveyId) throws SQLException, AwsSecretsManagerException {
		return 1;
	}

	@Override
	public int addQuestion(SurveyQuestionMapper surveyQuestionMapper) throws SQLException, AwsSecretsManagerException {
		return 1;
	}

	@Override
	public List<Question> getAllSurveyQuestions(int surveyId) throws SQLException, AwsSecretsManagerException {
		return getQuestions(2);
	}

	private List<Question> getQuestions(int size) {
		int i;
		List<Question> questionList = new ArrayList<>();
		for (i = 0; i < size; i++) {
			Question question = new Question();
			String questionTitle = String.format("Question Title %d", i);
			String questionText = String.format("Question Text %d", i);
			int questionType = (i + 1) % 4;
			question.setQuestionId(i);
			question.setQuestionTitle(questionTitle);
			question.setQuestionTypeId(questionType);
			question.setQuestionText(questionText);
			questionList.add(question);
		}
		return questionList;
	}

	@Override
	public void deleteSurveyQuestion(int questionId, int surveyId) throws SQLException, AwsSecretsManagerException {

	}

	@Override
	public List<Question> getSurveyQuestionByInstructorID(int instructorID) throws SQLException, AwsSecretsManagerException {
		return getQuestions(5);
	}

	@Override
	public List<Question> getSurveyQuestionByCourseID(int course) throws SQLException, AwsSecretsManagerException {
		return getQuestions(5);
	}

	@Override
	public List<Question> getRemainingQuestionsForSurvey(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException {
		return getQuestions(3);
	}
}
