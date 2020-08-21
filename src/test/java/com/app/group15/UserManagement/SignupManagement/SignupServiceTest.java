package com.app.group15.UserManagement.SignupManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.IUserManagementAbstractFactory;
import com.app.group15.UserManagement.User;
import com.app.group15.UserManagement.UserDaoMock;
import com.app.group15.UserManagement.UserRole;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SignupServiceTest {

    private UserDaoMock userDaoMock = new UserDaoMock();
    private ISignupService signupService = ServiceConfigForTest.getInstance().getSignupService();
    private IUserManagementAbstractFactory userManagementAbstractFactory = AppConfig.getInstance().getUserManagementAbstractFactory();

    @Test
    public void checkUserExistsTest() throws SQLException, AwsSecretsManagerException {
        boolean exist = signupService.checkUserExists("B00843469");
        assertEquals(exist, true);
    }

    @Test
    public void createUserTest() throws AllowedRolesNotSetException, SQLException, AwsSecretsManagerException {
        User user = (User) userManagementAbstractFactory.getUserModel();
        user.setBannerId("B00843469");

        int created = signupService.createUser(user, UserRole.GUEST.getRole());
        assertEquals(created, 1);
    }


}
