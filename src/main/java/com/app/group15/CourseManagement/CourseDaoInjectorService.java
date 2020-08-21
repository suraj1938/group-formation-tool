package com.app.group15.CourseManagement;


import com.app.group15.CourseManagement.Instructor.CourseInstructorMapperDao;
import com.app.group15.CourseManagement.Student.CourseStudentMapperDao;

public class CourseDaoInjectorService {

    private CourseDao courseDao;

    public CourseDaoInjectorService() {
        courseDao = new CourseDao();
        courseDao.injectCourseInstructorMapperDao(new CourseInstructorMapperDao());
        courseDao.injectCourseStudentMapperDao(new CourseStudentMapperDao());

    }

    public CourseDao getCourseDao() {
        return courseDao;
    }
}
