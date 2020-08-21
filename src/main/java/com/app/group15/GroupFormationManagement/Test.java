package com.app.group15.GroupFormationManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.User;

public class Test {

	public static void main(String[] args) {
		
		GroupFormationService service=new GroupFormationServiceInjector().getGroupFormationService();
		try {
			 ArrayList<ArrayList<User>> userList=service.formGroups(19, 3);
			 for(int i=0;i<userList.size();i++) {
				 for(int j=0;j<userList.get(i).size();j++) {
					 System.out.println(userList.get(i).get(j).getFirstName() +" Group " +i);
				 }
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AwsSecretsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

