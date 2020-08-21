package com.app.group15.QuestionManager;

import com.app.group15.Persistence.Persistence;

public interface IQuestionManagerAbstractFactory {

	public Persistence getQuestionModel();
	
	public Persistence getQuestionChoiceMapperModel();
	
	public QuestionChoiceMapperAbstractDao getQuestionChoiceMapperDao();
	
	public IQuestionManagerService getQuestionManagerService();
	
	public IQuestionChoiceMapperService getQuestionChoiceMapperService();
	
	public QuestionManagerAbstractDao getQuestionManagerDao();
	
	public Persistence getOptionsModel();
	
	public Persistence getQuestionTypeModel();
}
