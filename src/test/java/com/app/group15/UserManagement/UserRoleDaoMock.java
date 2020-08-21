package com.app.group15.UserManagement;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.Persistence;


@SuppressWarnings("rawtypes")
public class UserRoleDaoMock extends UserRoleAbstractDao{

    public UserRoleDaoMock() {

    }

    public Set<String> getRolesByBannerIdMock(String bannerId) {
        Set<String> roles = new HashSet<String>();
        switch (bannerId) {
            case "admin":
                roles.add("ADMIN");
                break;
            case "B00843800":
                roles.add("INSTRUCTOR");
                break;
            case "B00843468":
            case "B00843467":
                roles.add("STUDENT");
                break;
            default:
                roles.add("GUEST");
                break;
        }
        return roles;
    }

	@Override
	public Persistence get(int id) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRoleId(String role) throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set getRolesByBannerId(String bannerId) throws SQLException, AwsSecretsManagerException {
		Set<String> s=new HashSet();
		s.add("STUDENT");
		return s;
	}

	@Override
	public void addRole(int userId, String role)
			throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRole(int userId, String role)
			throws AllowedRolesNotSetException, SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getAll() throws SQLException, AwsSecretsManagerException {
		// TODO Auto-generated method stub
		return null;
	}


}
