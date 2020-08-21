package com.app.group15.CourseManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.Persistence;

public class CourseDaoMock extends CourseAbstractDao{

   

    
	@Override
	public Persistence get(int id) throws SQLException, AwsSecretsManagerException {
		Course course=new Course();
		course.setId(id);
		return course;
	}

	@Override
	public ArrayList getAll() throws SQLException, AwsSecretsManagerException {
		ArrayList<Course> courseList=new ArrayList();
		Course course=new Course();
		course.setName("Course 1");
		courseList.add(course);
		return courseList;
	}

	@Override
	public int save(Persistence persistence) throws SQLException, AwsSecretsManagerException {
		
		return 1;
	}

	@Override
	public void update(Persistence persistence, int id) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		
	}
}
