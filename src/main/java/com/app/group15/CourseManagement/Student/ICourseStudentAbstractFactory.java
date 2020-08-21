package com.app.group15.CourseManagement.Student;

import com.app.group15.Persistence.Persistence;

public interface ICourseStudentAbstractFactory {

    Persistence getCourseStudentMapperModel();

    CourseStudentMapperAbstractDao getCourseStudentMapperDao();
}
