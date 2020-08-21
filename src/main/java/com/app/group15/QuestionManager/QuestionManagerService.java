package com.app.group15.QuestionManager;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class QuestionManagerService implements IQuestionManagerServiceInjector, IQuestionManagerService {

    private QuestionManagerAbstractDao questionManagerDao;
    IQuestionManagerAbstractFactory questionManagerAbstractFactory=AppConfig.getInstance().getQuestionManagerAbstractFactory();

    @Override
    public void injectQuestionManagerInjectorService(QuestionManagerAbstractDao questionManagerDao) {
        this.questionManagerDao = questionManagerDao;
    }

    @Override
    public List<QuestionType> getQuestionType() throws SQLException, AwsSecretsManagerException {
        List<QuestionType> listOfQuestion = questionManagerDao.getAllQuestionTypes();
        listOfQuestion.forEach((QuestionType questionType) -> {
            questionType.setQuestionType(
                    QuestionTypeEnum.valueOf(questionType.getQuestionType()).getQuestionType());
        });
        return listOfQuestion;
    }


    @Override
    public Question formQuestion(String questionTitle, String questionText, int selectedOption) {
        Question question = (Question) questionManagerAbstractFactory.getQuestionModel();
        question.setQuestionTitle(questionTitle);
        question.setQuestionText(questionText);
        question.setQuestionTypeId(selectedOption);
        if (selectedOption == 2 || selectedOption == 3) {
            question.addOptions((Options)questionManagerAbstractFactory.getOptionsModel());
        }
        return question;
    }

    @Override
    public List<String> formPreview(Question question) {
        List<String> options = null;
        if ((question.getQuestionTypeId() == 2) || (question.getQuestionTypeId() == 3)) {
            options = new ArrayList<>();
            List<String> temp = options;
            question.getOptions().forEach((Options opt) -> {
                temp.add(opt.getOption());
            });
        }
        return options;
    }

    @Override
    public boolean insertQuestion(Question question, User user) throws SQLException, AwsSecretsManagerException {

        question.setQuestionInstructorId(user.getId());
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        question.setQuestionAddedDate(simpleDateFormat.format(currentDate));

        int insertedQuestionId = questionManagerDao.saveQuestion(question);
        if (question.getQuestionTypeId() == 2 || question.getQuestionTypeId() == 3) {
            if (insertedQuestionId != -1) {
                List<Options> options = question.getOptions();
                List<Integer> optionsIdList = new ArrayList<>();
                for (Options option : options) {
                    int optionID = questionManagerDao.saveOption(option);
                    optionsIdList.add(optionID);
                }

                optionsIdList.forEach((Integer insertedChoiceId) -> {
                    try {
						questionManagerDao.saveQuestionOptionMapping(insertedQuestionId, insertedChoiceId);
					} catch (SQLException | AwsSecretsManagerException e) {
						 GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
				         
					}
                });

            }
        }

        return false;
    }

    @Override
    public List<Question> getAllQuestionsOfInstructor(int instructorId, String sortColumn) throws SQLException, AwsSecretsManagerException {
        ArrayList<Question> questionArrayList = (ArrayList<Question>) questionManagerDao.getAllQuestionsOfInstructor(instructorId);
        switch (sortColumn) {
            case "questionId":
                questionArrayList.sort(Comparator.comparingInt(Question::getQuestionId));
                break;
            case "questionTitle":
                questionArrayList.sort(Comparator.comparing(Question::getQuestionTitle));
                break;
            case "questionType":
                questionArrayList.sort(Comparator.comparingInt(Question::getQuestionTypeId));
                break;
            case "questionText":
                questionArrayList.sort(Comparator.comparing(Question::getQuestionText));
                break;
            case "questionDate":
                questionArrayList.sort(Comparator.comparing(Question::getQuestionAddedDate));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sortColumn);
        }
        return questionArrayList;
    }

    @Override
    public Question getQuestion(int questionId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(questionId)) {
            return (Question) questionManagerDao.get(questionId);
        }
        return null;
    }

    @Override
    public List<String> getOptions(int questionId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(questionId)) {
            List<Options> optionsList = questionManagerDao.getOptions(questionId);
            List<String> optionsValueList = new ArrayList<>();
            optionsList.forEach(options -> optionsValueList.add(options.getValue()));
            return optionsValueList;
        } else {
            return null;
        }
    }


}
