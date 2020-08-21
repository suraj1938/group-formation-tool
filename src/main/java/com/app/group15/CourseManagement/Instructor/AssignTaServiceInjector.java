package com.app.group15.CourseManagement.Instructor;


import com.app.group15.CourseManagement.CourseDaoInjectorService;
import com.app.group15.CourseManagement.CourseServiceInjector;
import com.app.group15.UserManagement.UserDaoInjectorService;
import com.app.group15.UserManagement.UserRoleDaoInjectorService;
import com.app.group15.UserManagement.UserServiceInjector;

public class AssignTaServiceInjector {

    private AssignTAService assignTaService;

    public AssignTaServiceInjector() {
        assignTaService = new AssignTAService();
        assignTaService.injectCourseDao(new CourseDaoInjectorService().getCourseDao());
        assignTaService.injectCourseInstructorMapper(new CourseInstructorMapperDao());
        assignTaService.injectInstructorService(new InstructorServiceInjector().getInstructorService());
        assignTaService.injectUserDao(new UserDaoInjectorService().getUserDao());
        assignTaService.injectUserRoleDao(new UserRoleDaoInjectorService().getUserRoleDao());
        assignTaService.injectCourseService(new CourseServiceInjector().getCourseService());
        assignTaService.injectUserService(new UserServiceInjector().getUserService());

    }

    public AssignTAService getAssignTaService() {
        return assignTaService;
    }


}
