package com.app.group15.CourseManagement;

import com.app.group15.CourseManagement.Instructor.CourseInstructorMapperAbstractDao;
import com.app.group15.CourseManagement.Student.CourseStudentMapperAbstractDao;

public interface ICourseServiceInjector {

    void injectCourseDao(CourseAbstractDao courseDao);

    void injectCourseInstructorMapper(CourseInstructorMapperAbstractDao courseInstructorMapperDao);

    void injectCourseStudentMapper(CourseStudentMapperAbstractDao courseStudentMapperDao);
}
