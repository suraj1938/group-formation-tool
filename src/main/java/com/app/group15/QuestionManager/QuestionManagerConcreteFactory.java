package com.app.group15.QuestionManager;

import com.app.group15.Persistence.Persistence;

public class QuestionManagerConcreteFactory implements IQuestionManagerAbstractFactory {

	private static IQuestionManagerAbstractFactory questionManagerConcreteFactory;

	public static IQuestionManagerAbstractFactory getInstance() {
		if (null == QuestionManagerConcreteFactory.getUniqueInstance()) {
			questionManagerConcreteFactory = new QuestionManagerConcreteFactory();
		}
		return QuestionManagerConcreteFactory.questionManagerConcreteFactory;
	}

	private static IQuestionManagerAbstractFactory getUniqueInstance() {
		return questionManagerConcreteFactory;
	}

	@Override
	public Persistence getQuestionModel() {
		return new Question();
	}

	@Override
	public Persistence getQuestionChoiceMapperModel() {
		return new QuestionChoiceMapper();
	}

	@Override
	public QuestionChoiceMapperAbstractDao getQuestionChoiceMapperDao() {
		return new QuestionChoiceMapperDao();
	}

	@Override
	public IQuestionManagerService getQuestionManagerService() {
		return new QuestionManagerServiceInjector().getQuestionManagerService();
	}

	@Override
	public IQuestionChoiceMapperService getQuestionChoiceMapperService() {
		return new QuestionChoiceMapperServiceInjector().getQuestionChoiceMapperService();
	}

	@Override
	public QuestionManagerAbstractDao getQuestionManagerDao() {
		return new QuestionManagerDao();
	}

	@Override
	public Persistence getOptionsModel() {

		return new Options();
	}

	@Override
	public Persistence getQuestionTypeModel() {

		return new QuestionType();
	}

}
