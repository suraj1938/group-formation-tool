package com.app.group15.CourseManagement.Student;

public class CourseStudentMapperDaoInjectorService {

    private CourseStudentMapperDao courseStudentMapperDao;

    public CourseStudentMapperDaoInjectorService() {
        courseStudentMapperDao = new CourseStudentMapperDao();

    }

    public CourseStudentMapperDao getCourseStudentMapperDao() {
        return courseStudentMapperDao;
    }


}
