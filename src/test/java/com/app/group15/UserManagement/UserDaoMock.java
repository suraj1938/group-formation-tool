package com.app.group15.UserManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.Persistence;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoMock extends UserAbstractDao {
	private IUserManagementAbstractFactory userManagementAbstractFactory=AppConfig.getInstance().getUserManagementAbstractFactory();
    public User getUserByBannerIdMock(String bannerId) {
        User user = (User) userManagementAbstractFactory.getUserModel();
        if (bannerId.equals("B00843468")) {
            user.setFirstName("Daksh");
            user.setLastName("Patel");
            user.setEmail("daksh.patel@dal.ca");
            user.setBannerId("B00843468");
            user.setPassword("passwordTest");
            return user;
        } else {
            return user;
        }
    }

	@Override
	public Persistence get(int id) throws SQLException, AwsSecretsManagerException {
		return null;
	}

	@Override
	public User getUserByBannerId(String bannerId) throws SQLException, AwsSecretsManagerException {
        User user = (User) userManagementAbstractFactory.getUserModel();
        if (bannerId.equals("B00843468")) {
            user.setFirstName("Daksh");
            user.setLastName("Patel");
            user.setEmail("daksh.patel@dal.ca");
            user.setBannerId("B00843468");
            user.setPassword("passwordTest");
            return user;
        } else {
            user.setBannerId("B00843468");
            return user;
        }
    }

	@Override
	public ArrayList getAll() throws SQLException, AwsSecretsManagerException {
        ArrayList<User> userList = new ArrayList<>();
        User user1 = (User) userManagementAbstractFactory.getUserModel();
        user1.setFirstName("Daksh");
        user1.setLastName("Patel");
        user1.setEmail("daksh.patel@dal.ca");
        user1.setBannerId("B00843460");
        user1.setBannerId("passwordTest");
        userList.add(user1);
        return userList;
    }

	@Override
	public String getUserPassword(int userId) throws SQLException, AwsSecretsManagerException {

        return "pass 5";
    }

    @Override
    public int saveUser(User user, String role)
            throws AllowedRolesNotSetException, AwsSecretsManagerException, SQLException {
        return 1;
    }

    @Override
    public void update(Persistence persistence, int id) throws SQLException, AwsSecretsManagerException {

    }

    @Override
    public void updateUserRole(int userId, String role) throws SQLException, AwsSecretsManagerException {

    }

    public int updateUserRoleMock(int userId, String role) throws SQLException, AwsSecretsManagerException {

        return userId;
    }

    @Override
    public User getUserByEmailId(String emailId) throws SQLException, AwsSecretsManagerException {
        return null;
    }

}
