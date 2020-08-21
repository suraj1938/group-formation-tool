package com.app.group15.CourseManagement;

import com.app.group15.Persistence.Persistence;

public interface ICourseManagementAbstractFactory {

    Persistence getCourseModel();

    CourseAbstractDao getCourseDao();

    ICourseService getCourseService();


}
