package com.app.group15.SurveyManagement.student;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.InvokeStoredProcedure;
import com.app.group15.QuestionManager.IQuestionManagerAbstractFactory;
import com.app.group15.QuestionManager.Options;
import com.app.group15.QuestionManager.Question;
import com.app.group15.SurveyManagement.ISurveyManagementAbstractFactory;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class SurveyStudentDao extends SurveyStudentAbstractDao {

    private IQuestionManagerAbstractFactory questionManagerAbstractFactory = AppConfig.getInstance().getQuestionManagerAbstractFactory();
    private ISurveyManagementAbstractFactory surveyManagementAbstractFactory = AppConfig.getInstance().getSurveyManagementAbstractFactory();

    @Override
    public void saveNumericResponse(int questionId, int surveyId, int numericResponse, int userId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;

        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spAddSurveyNumericResponse(?,?,?,?)");
            invokeStoredProcedure.setParameter(1, questionId);
            invokeStoredProcedure.setParameter(2, surveyId);
            invokeStoredProcedure.setParameter(3, numericResponse);
            invokeStoredProcedure.setParameter(4, userId);

            invokeStoredProcedure.execute();

        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }

    @Override
    public void saveTextResponse(int questionId, int surveyId, String textResponse, int userId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;

        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spAddSurveyTextResponse(?,?,?,?)");
            invokeStoredProcedure.setParameter(1, questionId);
            invokeStoredProcedure.setParameter(2, surveyId);
            invokeStoredProcedure.setParameter(3, textResponse);
            invokeStoredProcedure.setParameter(4, userId);


            invokeStoredProcedure.execute();
            //insertedSurveyId = invokeStoredProcedure.getOutputParameter(2);

        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }

    @Override
    public void saveChoiceResponse(int questionId, int surveyId, String choiceId, int userId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;

        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spAddSurveyChoiceResponse(?,?,?,?)");
            invokeStoredProcedure.setParameter(1, questionId);
            invokeStoredProcedure.setParameter(2, surveyId);
            invokeStoredProcedure.setParameter(3, choiceId);
            invokeStoredProcedure.setParameter(4, userId);


            invokeStoredProcedure.execute();

        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }

    @Override
    public List<Question> getSurveyQuestionWithCourseByCourseID(int courseID) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;

        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spGetSurveyQuestionByCourseID(?)");
            invokeStoredProcedure.setParameter(1, courseID);
            ResultSet results = invokeStoredProcedure.executeWithResults();
            List<Question> questionList = new ArrayList<>();
            int surveyId;
            if (results != null) {
                while (results.next()) {
                    Question question = (Question) questionManagerAbstractFactory.getQuestionModel();
                    results.getInt("id");
                    question.setQuestionId(results.getInt("id"));

                    question.setQuestionTitle(results.getString("title"));
                    question.setQuestionTypeId(results.getInt("type_id"));
                    question.setQuestionInstructorId(results.getInt("instructor_id"));
                    question.setQuestionText(results.getString("question_text"));
                    question.setOptions(getQuestionOptionsByQuestionId(results.getInt("id")));
                    question.setQuestionAddedDate(results.getString("question_date"));
                    question.setSurveyId(results.getInt("survey_id"));
                    questionList.add(question);

                }
            }
            return questionList;
        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }

    @Override
    public List<Options> getQuestionOptionsByQuestionId(int questionId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;

        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spFindQuestionChoices(?)");
            invokeStoredProcedure.setParameter(1, questionId);
            ResultSet results = invokeStoredProcedure.executeWithResults();
            List<Options> optionsList = new ArrayList<>();
            if (results != null) {
                while (results.next()) {

                    Options options = (Options) questionManagerAbstractFactory.getOptionsModel();
                    options.setId(results.getInt("choice_id"));
                    options.setOption(results.getString("choice"));
                    options.setValue(results.getString("stored_as"));
                    optionsList.add(options);
                }
            }
            return optionsList;
        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }


    @Override
    public List<Integer> validateUserHasAppearedSurveyBefore(int userId, int surveyId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spValidateUserAppearedSurvey(?,?)");
            invokeStoredProcedure.setParameter(1, userId);
            invokeStoredProcedure.setParameter(2, surveyId);
            List<Integer> count = new ArrayList<>();
            ResultSet results = invokeStoredProcedure.executeWithResults();
            int choiceCount, numericCount, textCount;

            if (results != null) {
                while (results.next()) {
                    choiceCount = results.getInt("count_1");
                    numericCount = results.getInt("count_2");
                    textCount = results.getInt("count_3");

                    count.add(choiceCount);
                    count.add(numericCount);
                    count.add(textCount);
                }
            }
            return count;
        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }


    @Override
    public List<StudentResponseNumeric> getNumericStudentResponsesOfASurvey(int surveyId)
            throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        List<StudentResponseNumeric> responseList = new ArrayList<>();

        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spGetStudentNumericResponsesOfSurvey(?)");
            invokeStoredProcedure.setParameter(1, surveyId);
            ResultSet results = invokeStoredProcedure.executeWithResults();

            if (results != null) {
                while (results.next()) {
                    StudentResponseNumeric response = (StudentResponseNumeric) surveyManagementAbstractFactory.getStudentResponseNumericModel();
                    response.setNumericResponse(results.getInt("numeric_response"));
                    response.setQuestionId(results.getInt("question_id"));
                    response.setStudentId(results.getInt("student_id"));
                    response.setSurveyId(results.getInt("survey_id"));
                    response.setId(results.getInt("id"));
                    Question question = (Question) questionManagerAbstractFactory.getQuestionModel();
                    question.setQuestionText(results.getString("question_text"));
                    question.setQuestionTitle(results.getString("title"));
                    question.setQuestionInstructorId(results.getInt("instructor_id"));
                    question.setQuestionId(results.getInt("question_id"));
                    response.setQuestion(question);
                    responseList.add(response);


                }
            }
            return responseList;
        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }

    @Override
    public List<StudentResponseChoice> getChoiceStudentResponsesOfASurvey(int surveyId)
            throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        List<StudentResponseChoice> responseList = new ArrayList<>();

        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spGetStudentChoiceResponsesOfSurvey(?)");
            invokeStoredProcedure.setParameter(1, surveyId);
            ResultSet results = invokeStoredProcedure.executeWithResults();

            if (results != null) {
                while (results.next()) {
                    StudentResponseChoice response = (StudentResponseChoice) surveyManagementAbstractFactory.getStudentResponseChoiceModel();
                    response.setChoiceId(results.getInt("choice_id"));
                    response.setChoiceText(getChoiceValue(results.getInt("choice_id")));
                    response.setQuestionId(results.getInt("question_id"));
                    response.setStudentId(results.getInt("student_id"));
                    response.setSurveyId(results.getInt("survey_id"));
                    response.setId(results.getInt("id"));
                    Question question = (Question) questionManagerAbstractFactory.getQuestionModel();
                    question.setQuestionText(results.getString("question_text"));
                    question.setQuestionTitle(results.getString("title"));
                    question.setQuestionInstructorId(results.getInt("instructor_id"));
                    question.setQuestionId(results.getInt("question_id"));
                    response.setQuestion(question);
                    responseList.add(response);
                }
            }
            return responseList;

        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }

    @Override
    public String getChoiceValue(int choiceId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        String choice = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spGetChoiceValue(?)");
            invokeStoredProcedure.setParameter(1, choiceId);
            ResultSet results = invokeStoredProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    choice = results.getString("choice");
                }
            }
            return choice;
        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }

    }


    @Override
    public List<StudentResponseText> getTextStudentResponsesOfASurvey(int surveyId)
            throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        List<StudentResponseText> responseList = new ArrayList<>();
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spGetStudentTextResponsesOfSurvey(?)");
            invokeStoredProcedure.setParameter(1, surveyId);
            ResultSet results = invokeStoredProcedure.executeWithResults();
            if (results != null) {
                while (results.next()) {
                    StudentResponseText response = (StudentResponseText) surveyManagementAbstractFactory.getStudentResponseTextModel();
                    response.setTextResponse(results.getString("text"));
                    response.setQuestionId(results.getInt("question_id"));
                    response.setStudentId(results.getInt("student_id"));
                    response.setSurveyId(results.getInt("survey_id"));
                    response.setId(results.getInt("id"));
                    Question question = (Question) questionManagerAbstractFactory.getQuestionModel();
                    question.setQuestionText(results.getString("question_text"));
                    question.setQuestionTitle(results.getString("title"));
                    question.setQuestionInstructorId(results.getInt("instructor_id"));
                    question.setQuestionId(results.getInt("question_id"));
                    response.setQuestion(question);
                    responseList.add(response);
                }
            }
            return responseList;
        } catch (SQLException | AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }

}
