package com.app.group15.CourseManagement;

import com.app.group15.Persistence.Persistence;

public class CourseManagementConcreteFactory implements ICourseManagementAbstractFactory {

    private static ICourseManagementAbstractFactory courseManagementAbstractFactory;


    public static ICourseManagementAbstractFactory getInstance() {
        if (null == CourseManagementConcreteFactory.getUniqueInstance()) {
            courseManagementAbstractFactory = new CourseManagementConcreteFactory();
        }
        return CourseManagementConcreteFactory.courseManagementAbstractFactory;
    }

    private static ICourseManagementAbstractFactory getUniqueInstance() {
        return courseManagementAbstractFactory;
    }


    @Override
    public Persistence getCourseModel() {
        return new Course();
    }

    @Override
    public CourseAbstractDao getCourseDao() {
        return new CourseDaoInjectorService().getCourseDao();
    }

    @Override
    public ICourseService getCourseService() {
        return new CourseServiceInjector().getCourseService();
    }
}
