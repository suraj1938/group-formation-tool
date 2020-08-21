package com.app.group15.CourseManagement.Instructor;


import com.app.group15.CourseManagement.CourseDaoMock;

import com.app.group15.CourseManagement.CourseServiceInjectorForTest;

import com.app.group15.UserManagement.UserDaoMock;

import com.app.group15.UserManagement.UserRoleDaoMock;

import com.app.group15.UserManagement.UserServiceInjectorForTest;

public class AssignTaServiceInjectorForTest {
	 private AssignTAService assignTaService;
	 
	 public AssignTaServiceInjectorForTest() {
		 assignTaService=new AssignTAService();
		 assignTaService.injectCourseDao(new CourseDaoMock());
	     assignTaService.injectCourseInstructorMapper(new CourseInstructorMapperDaoMock());
	     assignTaService.injectInstructorService(new InstructorServiceInjectorForTest().getInstructorService());
	     assignTaService.injectUserDao(new UserDaoMock());
	     assignTaService.injectUserRoleDao(new UserRoleDaoMock());
	     assignTaService.injectCourseService(new CourseServiceInjectorForTest().getCourseService());
	     assignTaService.injectUserService(new UserServiceInjectorForTest().getUserService());
	 }

	public AssignTAService getAssignTaService() {
		return assignTaService;
	}

	
	 
	 
}
