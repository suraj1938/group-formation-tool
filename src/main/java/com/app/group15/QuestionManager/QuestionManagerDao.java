package com.app.group15.QuestionManager;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.InvokeStoredProcedure;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class QuestionManagerDao extends QuestionManagerAbstractDao {

	IQuestionManagerAbstractFactory questionManagerAbstractFactory=AppConfig.getInstance().getQuestionManagerAbstractFactory();
    @Override
    public Question get(int id) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spFindQuestion(?)");
            invokeStoredProcedure.setParameter(1, id);
            ResultSet results = invokeStoredProcedure.executeWithResults();
            Question question = (Question) questionManagerAbstractFactory.getQuestionModel();
            if (results != null) {
                while (results.next()) {
                    question.setQuestionId(results.getInt("id"));
                    question.setQuestionTitle(results.getString("title"));
                    question.setQuestionTypeId(results.getInt("type_id"));
                    question.setQuestionText(results.getString("question_text"));
                    question.setQuestionAddedDate(results.getString("question_date"));
                }
            }
            return question;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }


    @Override
    public List<Question> getAllQuestionsOfInstructor(int instructorId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spFindAllInstructorQuestions(?)");
            invokeStoredProcedure.setParameter(1, instructorId);
            ResultSet results = invokeStoredProcedure.executeWithResults();
            List<Question> questionList = new ArrayList<>();
            if (results != null) {
                while (results.next()) {
                    Question question = (Question) questionManagerAbstractFactory.getQuestionModel();
                    question.setQuestionId(results.getInt("id"));
                    question.setQuestionTitle(results.getString("title"));
                    question.setQuestionTypeId(results.getInt("type_id"));
                    question.setQuestionText(results.getString("question_text"));
                    question.setQuestionAddedDate(results.getString("question_date"));
                    questionList.add(question);
                }
            }
            return questionList;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
       
    }

    @Override
    public List<QuestionType> getAllQuestionTypes() throws SQLException, AwsSecretsManagerException {

        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spFindAllQuestionType()");
            ResultSet results = invokeStoredProcedure.executeWithResults();
            List<QuestionType> questionTypeList = new ArrayList<>();
            if (results != null) {
                while (results.next()) {
                    QuestionType type = new QuestionType();
                    type.setId(results.getInt("id"));
                    type.setQuestionType(results.getString("type"));
                    questionTypeList.add(type);
                }
            }
            return questionTypeList;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
        
    }

    @Override
    public int saveQuestion(Question question) throws SQLException, AwsSecretsManagerException {

        InvokeStoredProcedure invokeStoredProcedure = null;
        int insertedQuestionId = -1;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spAddQuestion(?,?,?,?,?,?)");
            invokeStoredProcedure.setParameter(1, question.getQuestionTitle());
            invokeStoredProcedure.setParameter(2, question.getQuestionTypeId());
            invokeStoredProcedure.setParameter(3, question.getQuestionInstructorId());
            invokeStoredProcedure.setParameter(4, question.getQuestionText());
            invokeStoredProcedure.setParameter(5, question.getQuestionAddedDate());
            invokeStoredProcedure.registerOutputParameterLong(6);

            invokeStoredProcedure.execute();
            insertedQuestionId = invokeStoredProcedure.getOutputParameter(6);

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
        return insertedQuestionId;
    }

    @Override
    public int saveOption(Options options) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        int insertedChoiceId = -1;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spAddChoice(?,?,?)");
            invokeStoredProcedure.setParameter(1, options.getOption());
            invokeStoredProcedure.setParameter(2, Integer.parseInt(options.getValue()));
            invokeStoredProcedure.registerOutputParameterLong(3);

            invokeStoredProcedure.execute();
            insertedChoiceId = invokeStoredProcedure.getOutputParameter(3);

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }


        return insertedChoiceId;
    }

    @Override
    public List<Options> getOptions(int questionId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spFindQuestionChoices(?)");
            invokeStoredProcedure.setParameter(1, questionId);
            ResultSet results = invokeStoredProcedure.executeWithResults();
            List<Options> optionsList = new ArrayList<>();
            if (results != null) {
                while (results.next()) {
                    Options options = (Options) questionManagerAbstractFactory.getOptionsModel();
                    options.setOption(results.getString("stored_as"));
                    options.setValue(results.getString("choice"));
                    optionsList.add(options);
                }
            }
            return optionsList;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
        
    }

    @Override
    public void saveQuestionOptionMapping(int insertedQuestionId, Integer insertedChoiceId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spMapQuestionChoice(?,?)");

            invokeStoredProcedure.setParameter(1, insertedQuestionId);
            invokeStoredProcedure.setParameter(2, insertedChoiceId);

            invokeStoredProcedure.execute();
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }
}
