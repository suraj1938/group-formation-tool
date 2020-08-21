package com.app.group15.CourseManagement.Instructor;

import com.app.group15.Persistence.Persistence;

public class CourseInstructorConcreteFactory implements ICourseInstructorAbstractFactory {

    private static ICourseInstructorAbstractFactory courseInstructorAbstractFactory;

    public static ICourseInstructorAbstractFactory getInstance() {
        if (null == CourseInstructorConcreteFactory.getUniqueInstance()) {
            courseInstructorAbstractFactory = new CourseInstructorConcreteFactory();
        }
        return CourseInstructorConcreteFactory.courseInstructorAbstractFactory;
    }

    private static ICourseInstructorAbstractFactory getUniqueInstance() {
        return courseInstructorAbstractFactory;
    }


    @Override
    public Persistence getCourseInstructorMapperModel() {
        return new CourseInstructorMapper();
    }

    @Override
    public CourseInstructorMapperAbstractDao getCourseInstructorMapperDao() {
        return new CourseInstructorMapperDaoInjectorService().getCourseInstructorMapperDao();
    }

    @Override
    public IAssignTAService getAssignTAService() {
        return new AssignTaServiceInjector().getAssignTaService();
    }

    @Override
    public IInstructorService getInstructorService() {
        return new InstructorServiceInjector().getInstructorService();
    }


}
