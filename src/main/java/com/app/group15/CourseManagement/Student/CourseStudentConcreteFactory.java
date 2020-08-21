package com.app.group15.CourseManagement.Student;

import com.app.group15.Persistence.Persistence;

public class CourseStudentConcreteFactory implements ICourseStudentAbstractFactory {

    private static ICourseStudentAbstractFactory courseStudentAbstractFactory;


    public static ICourseStudentAbstractFactory getInstance() {
        if (null == CourseStudentConcreteFactory.getUniqueInstance()) {
            courseStudentAbstractFactory = new CourseStudentConcreteFactory();
        }
        return CourseStudentConcreteFactory.courseStudentAbstractFactory;
    }

    private static ICourseStudentAbstractFactory getUniqueInstance() {
        return courseStudentAbstractFactory;
    }


    @Override
    public Persistence getCourseStudentMapperModel() {
        return new CourseStudentMapper();
    }

    @Override
    public CourseStudentMapperAbstractDao getCourseStudentMapperDao() {
        return new CourseStudentMapperDaoInjectorService().getCourseStudentMapperDao();
    }


}
