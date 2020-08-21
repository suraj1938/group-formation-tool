package com.app.group15.SurveyManagement;

import com.app.group15.Persistence.Persistence;

public class SurveyQuestionMapper extends Persistence {
	private int questionId;
	private int questionOrder;
	private int surveyId;
	private int ruleId;
	private int ruleValue;

	public int getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(int ruleValue) {
		this.ruleValue = ruleValue;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getQuestionOrder() {
		return questionOrder;
	}

	public void setQuestionOrder(int questionOrder) {
		this.questionOrder = questionOrder;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
}
