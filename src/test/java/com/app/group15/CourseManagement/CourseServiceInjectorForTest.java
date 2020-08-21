package com.app.group15.CourseManagement;

import com.app.group15.CourseManagement.Instructor.CourseInstructorMapperDaoMock;
import com.app.group15.CourseManagement.Student.CourseStudentMapperDaoMock;

public class CourseServiceInjectorForTest {
	
	private CourseService courseService;
	
	public CourseServiceInjectorForTest(){
		courseService=new CourseService();
		courseService.injectCourseDao(new CourseDaoMock());
		courseService.injectCourseInstructorMapper(new CourseInstructorMapperDaoMock());
		courseService.injectCourseStudentMapper(new CourseStudentMapperDaoMock());
	}

	public CourseService getCourseService() {
		return courseService;
	}
	
	

}
