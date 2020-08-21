package com.app.group15.CourseManagement.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

public class CourseStudentMapperDaoMock extends CourseStudentMapperAbstractDao{

	@Override
	public int addStudentToACourse(int courseId, int studentId) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletByCourseId(int courseId) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getAll() throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getCourseIdsOfAStudent(int studentId) throws SQLException, AwsSecretsManagerException {
		ArrayList<Integer> list=new ArrayList<>();
		list.add(1);
		return list;
	}

}
