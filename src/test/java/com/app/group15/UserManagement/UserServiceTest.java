package com.app.group15.UserManagement;

import com.app.group15.Config.ServiceConfigForTest;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class UserServiceTest {

    private UserDaoMock userDaoMock = new UserDaoMock();
    private IUserService userService = ServiceConfigForTest.getInstance().getUserService();

    @Test
    public void getAllUsersTest() throws SQLException, AwsSecretsManagerException {
        List<User> userList = userService.getAllUsers();
        assertNotNull(userList);
    }

    @Test
    public void updateUserRoleTest() throws AllowedRolesNotSetException, SQLException, AwsSecretsManagerException {
        userService.updateUserRole(1, UserRole.STUDENT.getRole());
        assertEquals(userDaoMock.updateUserRoleMock(1, UserRole.STUDENT.getRole()), 1);
    }
}
