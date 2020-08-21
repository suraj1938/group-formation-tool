package com.app.group15.GroupFormationManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.QuestionManager.IQuestionManagerService;
import com.app.group15.QuestionManager.Question;
import com.app.group15.QuestionManager.QuestionType;
import com.app.group15.QuestionManager.QuestionTypeEnum;
import com.app.group15.SurveyManagement.ISurveyService;
import com.app.group15.SurveyManagement.SurveyQuestionMapper;
import com.app.group15.SurveyManagement.student.*;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

public class GroupFormationAlgorithmService implements IGroupFormationAlgorithmService, IGroupFormationAlgorithmServiceInjector {

    private ISurveyService surveyService;
    private ISurveyStudentService surveyStudentService;
    private IQuestionManagerService questionService;
    private int surveyId;
    private List<Integer> questionOrderList;


    @Override
    public int getSurveyIdForACourse(int courseId) throws SQLException, AwsSecretsManagerException {
        surveyId = surveyService.getSurveyIdOfACourse(courseId);
        return surveyId;
    }

    private List<Integer> getQuestionOrder(int surveyId) throws SQLException, AwsSecretsManagerException {
        ArrayList<SurveyQuestionMapper> list = surveyService.getQuestionsOfASurveySortedByOrder(surveyId);
        List<Integer> questionOrder = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            questionOrder.add(list.get(i).getQuestionId());
        }
        return questionOrder;
    }
    
    public ArrayList<Integer> getQuestionOrderNumbers(int surveyId) throws SQLException, AwsSecretsManagerException{
		ArrayList<SurveyQuestionMapper> list = surveyService.getQuestionsOfASurveySortedByOrder(surveyId);
		ArrayList<Integer> questionOrderNumber=new ArrayList();
		
		for (int i = 0; i < list.size(); i++) {
			questionOrderNumber.add(list.get(i).getQuestionOrder());
			
		}
		

		return questionOrderNumber;
	}


    @Override
    public HashMap<Integer, String> getQuestionCriteriaOfSurveyInOrder(int surveyId)
            throws SQLException, AwsSecretsManagerException {
        ArrayList<SurveyQuestionMapper> list = surveyService.getQuestionsOfASurveySortedByOrder(surveyId);
        HashMap<Integer, String> questionCriteriaMap = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            questionCriteriaMap.put(list.get(i).getQuestionOrder(), surveyService.getRuleFromId(list.get(i).getRuleId()));
        }
        return questionCriteriaMap;
    }

    @Override
    public ArrayList<StudentResponseMaintainer> getStudentResponsesSortedInQuestionOrder(int surveyId)
            throws SQLException, AwsSecretsManagerException {
        List<StudentResponseChoice> choiceResponseList = surveyStudentService
                .getChoiceStudentResponsesForASurvey(surveyId);
        List<StudentResponseNumeric> numericResponseList = surveyStudentService
                .getNumericStudentResponsesForASurvey(surveyId);
        List<StudentResponseText> textResponseList = surveyStudentService.getTextStudentResponsesForASurvey(surveyId);

        return arrangeStudentResponsesByQuestionOrder(choiceResponseList, numericResponseList, textResponseList);
    }

    private ArrayList<StudentResponseMaintainer> arrangeStudentResponsesByQuestionOrder(List<StudentResponseChoice> choiceResponseList,
                                                                                        List<StudentResponseNumeric> numericResponseList, List<StudentResponseText> textResponseList) throws SQLException, AwsSecretsManagerException {

        ArrayList<StudentResponseMaintainer> finalStudentResponses = new ArrayList();
        HashMap<Integer, List<StudentResponse>> studentResponseMap = new HashMap();

        for (int i = 0; i < choiceResponseList.size(); i++) {
            if (studentResponseMap.get(choiceResponseList.get(i).getStudentId()) == null) {
                ArrayList<StudentResponse> allResponsesOfAStudent = new ArrayList<>();
                allResponsesOfAStudent.add(choiceResponseList.get(i));
                studentResponseMap.put(choiceResponseList.get(i).getStudentId(), allResponsesOfAStudent);
            } else {
                studentResponseMap.get(choiceResponseList.get(i).getStudentId()).add(choiceResponseList.get(i));
            }
        }

        for (int i = 0; i < numericResponseList.size(); i++) {
            if (studentResponseMap.get(numericResponseList.get(i).getStudentId()) == null) {
                ArrayList<StudentResponse> allResponsesOfAStudent = new ArrayList<>();
                allResponsesOfAStudent.add(numericResponseList.get(i));
                studentResponseMap.put(numericResponseList.get(i).getStudentId(), allResponsesOfAStudent);
            } else {
                studentResponseMap.get(numericResponseList.get(i).getStudentId()).add(numericResponseList.get(i));
            }
        }

        for (int i = 0; i < textResponseList.size(); i++) {
            if (studentResponseMap.get(textResponseList.get(i).getStudentId()) == null) {
                ArrayList<StudentResponse> allResponsesOfAStudent = new ArrayList<>();
                allResponsesOfAStudent.add(textResponseList.get(i));
                studentResponseMap.put(textResponseList.get(i).getStudentId(), allResponsesOfAStudent);
            } else {
                studentResponseMap.get(textResponseList.get(i).getStudentId()).add(textResponseList.get(i));
            }
        }

        for (Entry<Integer, List<StudentResponse>> entry : studentResponseMap.entrySet()) {
            entry.setValue(arrangeResponsesOfAStudentByQuestionOrder(entry.getValue()));
        }

        for (Entry<Integer, List<StudentResponse>> entry : studentResponseMap.entrySet()) {
            List<StudentResponse> orderedResponseList = entry.getValue();
            for (int i = 0; i < orderedResponseList.size(); i++) {

                if (orderedResponseList.get(i).getClass() == StudentResponseChoice.class) {
                    StudentResponseChoice obj = (StudentResponseChoice) orderedResponseList.get(i);
                    String questionType = findQuestionType(obj.getQuestionId());
                    if (questionType == QuestionTypeEnum.MCQ_MC.getQuestionType()) {
                        setCumulativeValueForMCQMultiSelect(obj.getQuestionId(), orderedResponseList);
                    }

                }
            }
        }

        for (Entry<Integer, List<StudentResponse>> entry : studentResponseMap.entrySet()) {
            entry.setValue(removeMultipleMCQResponses(entry.getValue()));
            List<StudentResponse> studentResponse = entry.getValue();
            ArrayList<Object> studentResponsesAsObject = new ArrayList();
            for (StudentResponse res : studentResponse) {
                if (res.getClass() == StudentResponseNumeric.class) {
                    StudentResponseNumeric obj = (StudentResponseNumeric) res;
                    studentResponsesAsObject.add(obj.getNumericResponse());

                } else if (res.getClass() == StudentResponseText.class) {
                    StudentResponseText obj = (StudentResponseText) res;
                    studentResponsesAsObject.add(obj.getTextResponse());

                } else if (res.getClass() == StudentResponseChoice.class) {
                    StudentResponseChoice obj = (StudentResponseChoice) res;
                    if (obj.getCumulativeValue() != null) {
                        studentResponsesAsObject.add(obj.getCumulativeValue());
                    } else {
                        studentResponsesAsObject.add(obj.getChoiceId());
                    }
                }
            }
            StudentResponseMaintainer responseMaintainer = new StudentResponseMaintainer();
            responseMaintainer.setResponseList(studentResponsesAsObject);
            responseMaintainer.setStudentId(entry.getKey());
            finalStudentResponses.add(responseMaintainer);
        }
        return finalStudentResponses;
    }

    private void setCumulativeValueForMCQMultiSelect(int questionId, List<StudentResponse> orderedResponseList) {
        Integer cumulativeValue = new Integer(0);

        for (int i = 0; i < orderedResponseList.size(); i++) {
            if (orderedResponseList.get(i).getQuestionId() == questionId) {
                StudentResponseChoice obj = (StudentResponseChoice) orderedResponseList.get(i);
                cumulativeValue += obj.getChoiceId();
                obj.setCumulativeValue(cumulativeValue);
            }
        }

    }

    private List<StudentResponse> removeMultipleMCQResponses(List<StudentResponse> studentResponse) {
        Set<Integer> questionIdsSet = new HashSet<Integer>();
        List<StudentResponse> studentResponseNew = new ArrayList();
        for (int i = 0; i < studentResponse.size(); i++) {
            if (questionIdsSet.add(studentResponse.get(i).getQuestionId())) {
                studentResponseNew.add(studentResponse.get(i));
            }
        }
        return studentResponseNew;
    }

    private String findQuestionType(int questionId) throws SQLException, AwsSecretsManagerException {
        Question question = questionService.getQuestion(questionId);
        int questionTypeId = question.getQuestionTypeId();
        List<QuestionType> questionTypeList = questionService.getQuestionType();
        for (int i = 0; i < questionTypeList.size(); i++) {
            if (questionTypeList.get(i).getId() == questionTypeId) {
                return questionTypeList.get(i).getQuestionType();
            }
        }
        return null;
    }

    private List<StudentResponse> arrangeResponsesOfAStudentByQuestionOrder(List<StudentResponse> responseList) throws SQLException, AwsSecretsManagerException {
        if (questionOrderList == null) {
            questionOrderList = getQuestionOrder(surveyId);
        }

        for (int i = 0; i < responseList.size(); i++) {
            if (responseList.get(i).getClass() == StudentResponseNumeric.class) {
                StudentResponseNumeric obj = (StudentResponseNumeric) responseList.get(i);
                obj.setQuestionOrder(questionOrderList.indexOf(obj.getQuestionId()) + 1);
            } else if (responseList.get(i).getClass() == StudentResponseText.class) {
                StudentResponseText obj = (StudentResponseText) responseList.get(i);
                obj.setQuestionOrder(questionOrderList.indexOf(obj.getQuestionId()) + 1);
            } else if (responseList.get(i).getClass() == StudentResponseChoice.class) {
                StudentResponseChoice obj = (StudentResponseChoice) responseList.get(i);
                obj.setQuestionOrder(questionOrderList.indexOf(obj.getQuestionId()) + 1);
            }
        }
        Collections.sort(responseList);
        return responseList;
    }

    @Override
    public void injectSurveyService(ISurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public void injectSurveyStudentService(ISurveyStudentService surveyStudentService) {
        this.surveyStudentService = surveyStudentService;
    }

    @Override
    public void injectQuestionService(IQuestionManagerService questionService) {
        this.questionService = questionService;
    }

}
