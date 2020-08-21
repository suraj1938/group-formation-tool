package com.app.group15.CourseManagement.Instructor;

import com.app.group15.CourseManagement.CourseDaoInjectorService;
import com.app.group15.CourseManagement.CourseDaoMock;
import com.app.group15.CourseManagement.Student.CourseStudentMapperDao;
import com.app.group15.CourseManagement.Student.CourseStudentMapperDaoMock;
import com.app.group15.UserManagement.UserDaoInjectorService;
import com.app.group15.UserManagement.UserDaoMock;
import com.app.group15.UserManagement.UserRoleDao;
import com.app.group15.UserManagement.UserRoleDaoMock;

public class InstructorServiceInjectorForTest {
	private InstructorService instructorService;
	
	public InstructorServiceInjectorForTest() {
		instructorService = new InstructorService();
        instructorService.injectCourseDao(new CourseDaoMock());
        instructorService.injectCourseInstructorMapper(new CourseInstructorMapperDaoMock());
        instructorService.injectCourseStudentMapper(new CourseStudentMapperDaoMock());
        instructorService.injectUserDao(new UserDaoMock());
        instructorService.injectUserRoleDao(new UserRoleDaoMock());
	}
	
	 public InstructorService getInstructorService() {
	        return instructorService;
	    }


}
