package com.app.group15.CourseManagement.Student;

import com.app.group15.Persistence.Persistence;

public class CourseStudentMapper extends Persistence {
    private int courseId;
    private int studentId;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

}
