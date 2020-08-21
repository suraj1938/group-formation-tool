package com.app.group15.CourseManagement.Instructor;

import com.app.group15.Persistence.Persistence;

public interface ICourseInstructorAbstractFactory {

    Persistence getCourseInstructorMapperModel();

    CourseInstructorMapperAbstractDao getCourseInstructorMapperDao();

    IAssignTAService getAssignTAService();

    IInstructorService getInstructorService();
}
