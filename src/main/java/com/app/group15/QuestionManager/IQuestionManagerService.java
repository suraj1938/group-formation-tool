package com.app.group15.QuestionManager;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;
import java.util.List;

public interface IQuestionManagerService {

    List<QuestionType> getQuestionType() throws SQLException, AwsSecretsManagerException;

    Question formQuestion(String questionTitle, String questionText, int selectedOption);

    List<String> formPreview(Question question);

    boolean insertQuestion(Question question, User user) throws SQLException, AwsSecretsManagerException;

    List<Question> getAllQuestionsOfInstructor(int instructorId, String sortColumn) throws SQLException, AwsSecretsManagerException;

    Question getQuestion(int questionId) throws SQLException, AwsSecretsManagerException;

    List<String> getOptions(int questionId)  throws SQLException, AwsSecretsManagerException;
}
