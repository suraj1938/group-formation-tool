package com.app.group15.SurveyManagement;

import com.app.group15.Persistence.Persistence;

public class Survey extends Persistence {
	private int courseId;
	private int publishState;

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getPublishState() {
		return publishState;
	}

	public void setPublishState(int publishState) {
		this.publishState = publishState;
	}
}
