package com.app.group15.CourseManagement.Instructor;

import com.app.group15.CourseManagement.CourseAbstractDao;
import com.app.group15.CourseManagement.Student.CourseStudentMapperAbstractDao;
import com.app.group15.UserManagement.UserAbstractDao;
import com.app.group15.UserManagement.UserRoleAbstractDao;

public interface IInstructorServiceInjector {
    void injectUserDao(UserAbstractDao userDao);

    void injectUserRoleDao(UserRoleAbstractDao userRoleDao);

    void injectCourseDao(CourseAbstractDao courseDao);

    void injectCourseInstructorMapper(CourseInstructorMapperAbstractDao courseInstructorMapperDao);

    void injectCourseStudentMapper(CourseStudentMapperAbstractDao courseStudentMapperDao);
}
