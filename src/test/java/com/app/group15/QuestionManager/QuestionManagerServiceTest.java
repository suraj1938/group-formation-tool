package com.app.group15.QuestionManager;

import com.app.group15.Config.AppConfig;
import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.IUserManagementAbstractFactory;
import com.app.group15.UserManagement.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionManagerServiceTest {

    private QuestionManagerDaoMock questionManagerDaoMock = new QuestionManagerDaoMock();
    private IQuestionManagerService questionManagerService = ServiceConfigForTest.getInstance().getQuestionManagerService();
	private IUserManagementAbstractFactory userManagementAbstractFactory=AppConfig.getInstance().getUserManagementAbstractFactory();

    @Test
    public void getQuestionTypeTest() throws SQLException, AwsSecretsManagerException {
        List<QuestionType> allQuestionType = questionManagerService.getQuestionType();
        assertEquals(allQuestionType.get(0).getId(), 1);
        assertNotNull(allQuestionType);
    }

    @Test
    public void formQuestionTest() {
        String questionTitle = "This is question title";
        String questionText = "This is question";
        int selectedOption = 1;
        Question question = questionManagerService.formQuestion(questionTitle, questionText, selectedOption);
        assertEquals(question.getQuestionText(), "This is question");
        assertNotEquals(question.getQuestionTitle(), "This is question");
    }

    @Test
    public void formPreviewTest() {
        List<Options> op = new ArrayList<>();
        Options options = new Options();
        options.setOption("option 1");
        options.setValue("1");
        Options options2 = new Options();
        options2.setOption("option 2");
        options2.setValue("2");
        op.add(options);
        op.add(options2);

        Question question = new Question();
        question.setQuestionTypeId(2);
        question.setOptions(op);

        List<String> responseOptions = questionManagerService.formPreview(question);

        assertNotNull(options);

        assertEquals(responseOptions.get(0), "option 1");
        assertEquals(responseOptions.get(1), "option 2");
    }

    @Test
    public void insertQuestionTest() throws SQLException, AwsSecretsManagerException {
        Question qst = questionManagerDaoMock.formQuestion();
        User user = (User) userManagementAbstractFactory.getUserModel();
        user.setId(1);
        Question insertedQuestion = questionManagerDaoMock.insertQuestion(qst, user);
        assertNotEquals(questionManagerService.insertQuestion(qst, user), true);
        assertNotNull(insertedQuestion);
        assertEquals(insertedQuestion.getQuestionInstructorId(), 1);
    }

    @Test
    public void getAllQuestionsOfInstructorTest() throws SQLException, AwsSecretsManagerException {
        List<Question> questionId = questionManagerService.getAllQuestionsOfInstructor(1, "questionId");
        for (int i = 0; i < questionId.size(); i++) {
            Question question = questionId.get(i);
            assertEquals(question.getQuestionId(), i);
        }
    }

    @Test
    public void getQuestion() throws SQLException, AwsSecretsManagerException {
        Question question = questionManagerService.getQuestion(1);
        assertEquals(question.getQuestionInstructorId(), 1);
    }
}
