package com.app.group15.CourseManagement.Instructor;

public class CourseInstructorMapperDaoInjectorService {

    private CourseInstructorMapperDao courseInstructorMapperDao;

    public CourseInstructorMapperDaoInjectorService() {
        courseInstructorMapperDao = new CourseInstructorMapperDao();
    }

    public CourseInstructorMapperDao getCourseInstructorMapperDao() {
        return courseInstructorMapperDao;
    }


}
