package com.app.group15.CourseManagement.Instructor;

import com.app.group15.Config.AppConfig;
import com.app.group15.CourseManagement.Course;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.IUserManagementAbstractFactory;
import com.app.group15.UserManagement.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseInstructorMapperDaoMock extends CourseInstructorMapperAbstractDao{

    private CourseInstructorMapper courseInstructorMapperEntity = new CourseInstructorMapper();
    private IUserManagementAbstractFactory userManagementAbstractFactory=AppConfig.getInstance().getUserManagementAbstractFactory();

	@Override
	public User getCourseInstructor(int id) throws SQLException, AwsSecretsManagerException {
		User user=(User) userManagementAbstractFactory.getUserModel();
		user.setId(id);
		return user;
	}

	@Override
	public List getCourseByInstructor(int id) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getCoursesByInstructor(int taId) throws AwsSecretsManagerException, SQLException {
		List<Course> courseList=new ArrayList();
		Course course=new Course();
		course.setId(1);
		courseList.add(course);
		return courseList;
	}

	@Override
	public Course getCourseByTa(int id) throws AwsSecretsManagerException, SQLException {
		Course course=new Course();
		course.setId(1);
		return course;
	}

	@Override
	public void deleteByCourseId(int courseId) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean doesCourseIdExistInThisMapper(int courseId) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void addInstructorForCourseWithTa(int courseId, int instructorId)
			throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addTaForCourseWithInstructor(int courseId, int taId)
			throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addInstructorToACourse(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTaToACourse(int courseId, int taId) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub

	}

	@Override
	public List getAll() throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getCourseTA(int userId) throws SQLException, AwsSecretsManagerException {
		User user=(User) userManagementAbstractFactory.getUserModel();
		user.setId(1);
		return user;
	}

}
