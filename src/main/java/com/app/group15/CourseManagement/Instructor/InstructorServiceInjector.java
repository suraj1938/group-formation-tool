package com.app.group15.CourseManagement.Instructor;

import com.app.group15.CourseManagement.CourseDaoInjectorService;
import com.app.group15.CourseManagement.Student.CourseStudentMapperDao;
import com.app.group15.UserManagement.UserDaoInjectorService;
import com.app.group15.UserManagement.UserRoleDao;

public class InstructorServiceInjector {

    private InstructorService instructorService;

    public InstructorServiceInjector() {
        instructorService = new InstructorService();
        instructorService.injectCourseDao(new CourseDaoInjectorService().getCourseDao());
        instructorService.injectCourseInstructorMapper(new CourseInstructorMapperDao());
        instructorService.injectCourseStudentMapper(new CourseStudentMapperDao());
        instructorService.injectUserDao(new UserDaoInjectorService().getUserDao());
        instructorService.injectUserRoleDao(new UserRoleDao());

    }

    public InstructorService getInstructorService() {
        return instructorService;
    }


}
