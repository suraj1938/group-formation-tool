package com.app.group15.GroupFormationManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public interface IGroupFormationAlgorithmService {
	
	public int getSurveyIdForACourse(int courseId) throws SQLException, AwsSecretsManagerException;
	public HashMap<Integer, String> getQuestionCriteriaOfSurveyInOrder(int surveyId)
			throws SQLException, AwsSecretsManagerException;
	public ArrayList<StudentResponseMaintainer> getStudentResponsesSortedInQuestionOrder(int surveyId)
			throws SQLException, AwsSecretsManagerException;
	public ArrayList<Integer> getQuestionOrderNumbers(int surveyId) throws SQLException, AwsSecretsManagerException;
}
