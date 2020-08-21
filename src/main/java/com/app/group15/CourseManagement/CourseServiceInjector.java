package com.app.group15.CourseManagement;

import com.app.group15.CourseManagement.Instructor.CourseInstructorMapperDao;
import com.app.group15.CourseManagement.Student.CourseStudentMapperDao;

public class CourseServiceInjector {

    private CourseService courseService;

    public CourseServiceInjector() {
        courseService = new CourseService();
        courseService.injectCourseDao(new CourseDaoInjectorService().getCourseDao());
        courseService.injectCourseInstructorMapper(new CourseInstructorMapperDao());
        courseService.injectCourseStudentMapper(new CourseStudentMapperDao());
    }

    public CourseService getCourseService() {
        return courseService;
    }

}
