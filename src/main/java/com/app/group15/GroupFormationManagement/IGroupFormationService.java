package com.app.group15.GroupFormationManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;

public interface IGroupFormationService {
	public  ArrayList<ArrayList<User>> formGroups(int courseId,int groupSize) throws SQLException, AwsSecretsManagerException;

}
