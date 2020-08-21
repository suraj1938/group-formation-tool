package com.app.group15.SurveyManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.QuestionManager.Question;
import com.app.group15.QuestionManager.QuestionManagerAbstractDao;
import com.app.group15.SurveyManagement.student.*;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class SurveyService implements ISurveyService, ISurveyServiceInjector {

    ISurveyManagementAbstractFactory surveyManagementAbstractFactory = AppConfig.getInstance().getSurveyManagementAbstractFactory();

    private SurveyAbstractDao surveyDao;
    private SurveyQuestionMapperAbstractDao surveyQuestionMapperDao;
    private QuestionManagerAbstractDao questionManagerDao;
    private SurveyStudentAbstractDao surveyStudentDao;

    @Override
    public List<Question> getSurveyQuestionByInstructorID(int instructorID) throws SQLException, AwsSecretsManagerException {
        return surveyQuestionMapperDao.getSurveyQuestionByInstructorID(instructorID);
    }

    @Override
    public Survey getSurveyByCourseId(int courseId) throws SQLException, AwsSecretsManagerException {
        return surveyDao.getSurveyByCourseID(courseId);
    }

    public List<Question> getSurveyQuestionByCourseID(int courseID) throws SQLException, AwsSecretsManagerException {
        return surveyQuestionMapperDao.getSurveyQuestionByCourseID(courseID);
    }

    @Override
    public List<Question> getRemainingQuestionsForSurvey(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException {
        return this.surveyQuestionMapperDao.getRemainingQuestionsForSurvey(courseId, instructorId);
    }


    @Override
    public void createSurveyIfNotExists(int courseId) throws SQLException, AwsSecretsManagerException {
        Survey survey = surveyDao.getSurveyByCourseID(courseId);
        if (survey.getId() == 0) {
            survey.setCourseId(courseId);
            survey.setPublishState(0);
            surveyDao.saveSurvey(survey);
        }
    }

    @Override
    public int addQuestionToSurvey(int courseId, int questionId, String rule, int ruleValue) throws SQLException, AwsSecretsManagerException {
        createSurveyIfNotExists(courseId);
        Question question = (Question) questionManagerDao.get(questionId);
        int ruleId = surveyDao.getRuleIdByRuleAndQuestionType(rule, question.getQuestionTypeId());
        SurveyQuestionMapper surveyQuestionMapper = (SurveyQuestionMapper) surveyManagementAbstractFactory.getSurveyQuestionMapperModel();
        int surveyId = surveyDao.getSurveyByCourseID(courseId).getId();
        surveyQuestionMapper.setSurveyId(surveyId);
        surveyQuestionMapper.setQuestionId(questionId);
        surveyQuestionMapper.setRuleId(ruleId);
        surveyQuestionMapper.setRuleValue(ruleValue);
        return surveyQuestionMapperDao.addQuestion(surveyQuestionMapper);
    }

    @Override
    public void deleteSurveyQuestion(int questionId, int courseId) throws SQLException, AwsSecretsManagerException {
        Survey survey = surveyDao.getSurveyByCourseID(courseId);
        surveyQuestionMapperDao.deleteSurveyQuestion(questionId, survey.getId());
    }

    @Override
    public void publishSurvey(int courseId) throws SQLException, AwsSecretsManagerException {
        Survey survey = surveyDao.getSurveyByCourseID(courseId);
        surveyDao.publishSurvey(survey);
    }

    @Override
    public void unPublishSurvey(int courseId) throws SQLException, AwsSecretsManagerException {
        Survey survey = surveyDao.getSurveyByCourseID(courseId);
        surveyDao.unPublishSurvey(survey);
    }


    @Override
    public void injectSurveyDao(SurveyAbstractDao surveyDao) {
        this.surveyDao = surveyDao;

    }

    @Override
    public void injectSurveyQuestionMapperDao(SurveyQuestionMapperAbstractDao surveyQuestionDao) {
        this.surveyQuestionMapperDao = surveyQuestionDao;
        try {
            surveyDao.getSurveyByCourseID(1).getId();
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void injectQuestionManagerDao(QuestionManagerAbstractDao questionManagerDao) {
        this.questionManagerDao = questionManagerDao;
    }

    @Override
    public ArrayList<SurveyQuestionMapper> getQuestionsOfASurveySortedByOrder(int surveyId)
            throws SQLException, AwsSecretsManagerException {
        return this.surveyDao.getQuestionsOfASurveySortedByOrder(surveyId);
    }

    @Override
    public int getSurveyIdOfACourse(int courseId) throws SQLException, AwsSecretsManagerException {
        return this.surveyDao.getSurveyIdOfACourse(courseId);
    }

    @Override
    public String getRuleFromId(int ruleId) throws SQLException, AwsSecretsManagerException {
        return this.surveyDao.getRuleFromId(ruleId);
    }

    @Override
    public void injectSurveyStudentDao(SurveyStudentAbstractDao surveyStudentDao) {
        this.surveyStudentDao = surveyStudentDao;
    }

    @Override
    public List<SurveyUserResponse> getSurveyResponse(String courseId) throws SQLException, AwsSecretsManagerException {
        Survey survey = surveyDao.getSurveyByCourseID(Integer.parseInt(courseId));
        List<StudentResponseNumeric> numericResponse = surveyStudentDao.getNumericStudentResponsesOfASurvey(survey.getId());
        List<StudentResponseText> textResponse = surveyStudentDao.getTextStudentResponsesOfASurvey(survey.getId());
        List<StudentResponseChoice> choiceResponse = surveyStudentDao.getChoiceStudentResponsesOfASurvey(survey.getId());
        List<SurveyUserResponse> surveyUserResponses = new ArrayList<>();
        List<SurveyUserResponse> tmpSurveyUserResponses = new ArrayList<>();

        getNumericResponse(numericResponse, surveyUserResponses, tmpSurveyUserResponses);
        surveyUserResponses.addAll(tmpSurveyUserResponses);
        tmpSurveyUserResponses = new ArrayList<>();
        getTextResponse(textResponse, surveyUserResponses, tmpSurveyUserResponses);
        surveyUserResponses.addAll(tmpSurveyUserResponses);
        tmpSurveyUserResponses = new ArrayList<>();
        getChoiceResponse(choiceResponse, surveyUserResponses, tmpSurveyUserResponses);
        List<SurveyUserResponse> finalSurveyResponse = new ArrayList<>();
        for (SurveyUserResponse response : surveyUserResponses) {
            if (finalSurveyResponse.size() > 0) {
                boolean f = false;
                for (int i = 0; i < finalSurveyResponse.size(); i++) {
                    if (finalSurveyResponse.get(i).getUser().getId() != response.getUser().getId()) {
                        f = true;
                    } else {
                        f = false;
                        break;
                    }
                }
                if (f) {
                    finalSurveyResponse.add(response);
                }
            } else {
                finalSurveyResponse.add(response);
            }
        }

        return finalSurveyResponse;
    }

    private void getChoiceResponse(List<StudentResponseChoice> choiceResponse, List<SurveyUserResponse> surveyUserResponses, List<SurveyUserResponse> tmpSurveyUserResponses) throws SQLException, AwsSecretsManagerException {
        for (StudentResponseChoice response : choiceResponse) {
            User user = surveyDao.getUser(response.getStudentId());

            SurveyResponse surveyResponse = surveyManagementAbstractFactory.getSurveyResponseModel();
            surveyResponse.setChoiceId(String.valueOf(response.getChoiceId()));
            surveyResponse.setQuestionTypeId(3);
            surveyResponse.setChoiceText(response.getChoiceText());
            surveyResponse.setQuestionTitle(response.getQuestion().getQuestionTitle());
            surveyResponse.setQuestionInstructorId(response.getQuestion().getQuestionInstructorId());
            surveyResponse.setQuestionText(response.getQuestion().getQuestionText());
            surveyResponse.setQuestionId(response.getQuestionId());
            if (surveyUserResponses.size() == 0) {
                SurveyUserResponse surveyUserResponse = surveyManagementAbstractFactory.getSurveyUserResponse();
                surveyUserResponse.setUser(user);
                List<SurveyResponse> surveyResponseList = new ArrayList<>();
                surveyResponseList.add(surveyResponse);
                surveyUserResponse.setSurveyResponseList(surveyResponseList);
                surveyUserResponses.add(surveyUserResponse);
            } else {
                for (SurveyUserResponse surveyUserResponse : surveyUserResponses) {
                    if (surveyUserResponse.getUser().getId() == user.getId()) {
                        List<SurveyResponse> surveyResponseList = surveyUserResponse.getSurveyResponseList();
                        surveyResponseList.add(surveyResponse);
                        surveyUserResponse.setSurveyResponseList(surveyResponseList);
                    } else {
                        SurveyUserResponse surveyUserRespons = surveyManagementAbstractFactory.getSurveyUserResponse();
                        surveyUserRespons.setUser(user);
                        List<SurveyResponse> surveyResponseList = new ArrayList<>();
                        surveyResponseList.add(surveyResponse);
                        surveyUserRespons.setSurveyResponseList(surveyResponseList);
                        tmpSurveyUserResponses.add(surveyUserRespons);
                    }
                }
            }

        }
    }

    private void getTextResponse(List<StudentResponseText> textResponse, List<SurveyUserResponse> surveyUserResponses, List<SurveyUserResponse> tmpSurveyUserResponses) throws SQLException, AwsSecretsManagerException {
        for (StudentResponseText response : textResponse) {
            User user = surveyDao.getUser(response.getStudentId());

            SurveyResponse surveyResponse = surveyManagementAbstractFactory.getSurveyResponseModel();
            surveyResponse.setTextResponse(response.getTextResponse());
            surveyResponse.setQuestionTypeId(4);
            surveyResponse.setQuestionTitle(response.getQuestion().getQuestionTitle());
            surveyResponse.setQuestionInstructorId(response.getQuestion().getQuestionInstructorId());
            surveyResponse.setQuestionText(response.getQuestion().getQuestionText());
            surveyResponse.setQuestionId(response.getQuestionId());

            if (surveyUserResponses.size() == 0) {
                SurveyUserResponse surveyUserResponse = surveyManagementAbstractFactory.getSurveyUserResponse();
                surveyUserResponse.setUser(user);
                List<SurveyResponse> surveyResponseList = new ArrayList<>();
                surveyResponseList.add(surveyResponse);
                surveyUserResponse.setSurveyResponseList(surveyResponseList);
                surveyUserResponses.add(surveyUserResponse);
            } else {
                for (SurveyUserResponse surveyUserResponse : surveyUserResponses) {
                    if (surveyUserResponse.getUser().getId() == user.getId()) {
                        List<SurveyResponse> surveyResponseList = surveyUserResponse.getSurveyResponseList();
                        surveyResponseList.add(surveyResponse);
                        surveyUserResponse.setSurveyResponseList(surveyResponseList);
                    } else {
                        SurveyUserResponse surveyUserRespons = surveyManagementAbstractFactory.getSurveyUserResponse();
                        surveyUserRespons.setUser(user);
                        List<SurveyResponse> surveyResponseList = new ArrayList<>();
                        surveyResponseList.add(surveyResponse);
                        surveyUserRespons.setSurveyResponseList(surveyResponseList);
                        tmpSurveyUserResponses.add(surveyUserRespons);
                    }
                }
            }


        }
    }

    private void getNumericResponse(List<StudentResponseNumeric> numericResponse, List<SurveyUserResponse> surveyUserResponses, List<SurveyUserResponse> tmpSurveyUserResponses) throws SQLException, AwsSecretsManagerException {
        for (StudentResponseNumeric response : numericResponse) {
            User user = surveyDao.getUser(response.getStudentId());

            SurveyResponse surveyResponse = surveyManagementAbstractFactory.getSurveyResponseModel();
            surveyResponse.setNumericResponse(response.getNumericResponse());
            surveyResponse.setQuestionTypeId(1);
            surveyResponse.setQuestionTitle(response.getQuestion().getQuestionTitle());
            surveyResponse.setQuestionInstructorId(response.getQuestion().getQuestionInstructorId());
            surveyResponse.setQuestionText(response.getQuestion().getQuestionText());
            surveyResponse.setQuestionId(response.getQuestionId());

            if (surveyUserResponses.size() == 0) {
                SurveyUserResponse surveyUserResponse = surveyManagementAbstractFactory.getSurveyUserResponse();
                surveyUserResponse.setUser(user);
                List<SurveyResponse> surveyResponseList = new ArrayList<>();
                surveyResponseList.add(surveyResponse);
                surveyUserResponse.setSurveyResponseList(surveyResponseList);
                surveyUserResponses.add(surveyUserResponse);
            } else {

                for (SurveyUserResponse surveyUserResponse : surveyUserResponses) {
                    if (surveyUserResponse.getUser().getId() == user.getId()) {

                        List<SurveyResponse> surveyResponseList = surveyUserResponse.getSurveyResponseList();
                        surveyResponseList.add(surveyResponse);
                        surveyUserResponse.setSurveyResponseList(surveyResponseList);

                    } else {
                        SurveyUserResponse surveyUserRespons = new SurveyUserResponse();
                        surveyUserRespons.setUser(user);
                        List<SurveyResponse> surveyResponseList = new ArrayList<>();
                        surveyResponseList.add(surveyResponse);
                        surveyUserRespons.setSurveyResponseList(surveyResponseList);
                        tmpSurveyUserResponses.add(surveyUserRespons);
                    }
                }
            }
        }
    }


}
