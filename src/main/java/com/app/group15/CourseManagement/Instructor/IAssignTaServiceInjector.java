package com.app.group15.CourseManagement.Instructor;

import com.app.group15.CourseManagement.CourseAbstractDao;
import com.app.group15.CourseManagement.CourseService;
import com.app.group15.UserManagement.IUserService;
import com.app.group15.UserManagement.UserAbstractDao;
import com.app.group15.UserManagement.UserRoleAbstractDao;

public interface IAssignTaServiceInjector {

    void injectUserDao(UserAbstractDao userDao);

    void injectCourseDao(CourseAbstractDao courseDao);

    void injectUserRoleDao(UserRoleAbstractDao userRoleDao);

    void injectCourseInstructorMapper(CourseInstructorMapperAbstractDao courseInstructorMapperDao);

    void injectInstructorService(IInstructorService instructorService);

    void injectUserService(IUserService userService);

    void injectCourseService(CourseService courseService);

}
