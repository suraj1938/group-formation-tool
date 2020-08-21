package com.app.group15.SurveyManagement.student;

public abstract class StudentResponse implements Comparable<StudentResponse> {
    private Integer questionOrder;
    private Integer questionId;

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @Override
    public int compareTo(StudentResponse response) {
        return this.questionOrder.compareTo(response.questionOrder);
    }

}
