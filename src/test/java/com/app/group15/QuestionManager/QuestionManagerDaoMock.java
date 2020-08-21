package com.app.group15.QuestionManager;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionManagerDaoMock extends QuestionManagerAbstractDao {

    public Question formQuestion() {
        Question question = new Question();
        question.setQuestionInstructorId(1);
        question.setQuestionTypeId(1);
        question.setQuestionText("This is question");
        question.setQuestionTitle("Question");
        return question;
    }

    public List<Options> formPreview() {
        List<Options> op = new ArrayList<>();
        Options options = new Options();
        options.setOption("option 1");
        options.setValue("1");
        Options options2 = new Options();
        options2.setOption("option 2");
        options2.setValue("2");
        op.add(options);
        op.add(options2);
        return op;
    }

    public Question insertQuestion(Question question, User user) {
        question.setQuestionInstructorId(user.getId());
        return question;
    }

    @Override
    public Question get(int id) throws SQLException, AwsSecretsManagerException {
        Question question = new Question();
        question.setQuestionInstructorId(1);
        question.setQuestionTypeId(1);
        question.setQuestionText("This is question");
        question.setQuestionTitle("Question");
        return question;
    }

    @Override
    public List<Question> getAllQuestionsOfInstructor(int instructorId) {
        int i;
        List<Question> questionList = new ArrayList<>();
        for (i = 0; i < 5; i++) {
            Question question = new Question();
            String questionTitle = String.format("Question %d Title", i);
            String questionText = String.format("Question %d Text", i);
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
    public List getAllQuestionTypes() {
        List<QuestionType> list = new ArrayList<>();
        QuestionType questionType = new QuestionType();
        questionType.setQuestionType("NUMERIC");
        questionType.setId(1);
        list.add(questionType);

        QuestionType questionType_2 = new QuestionType();
        questionType_2.setQuestionType("MCQ_SC");
        questionType_2.setId(2);

        list.add(questionType_2);
        return list;
    }

    @Override
    public int saveQuestion(Question question) {
        return 0;
    }

    @Override
    public int saveOption(Options option) {
        return 0;
    }

    @Override
    public List<Options> getOptions(int questionId) {
        return null;
    }

    @Override
    public void saveQuestionOptionMapping(int insertedQuestionId, Integer insertedChoiceId) {

    }
}
