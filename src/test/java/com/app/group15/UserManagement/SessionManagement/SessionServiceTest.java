package com.app.group15.UserManagement.SessionManagement;

import com.app.group15.UserManagement.User;
import com.app.group15.UserManagement.UserDaoMock;
import com.app.group15.UserManagement.UserRoleDaoMock;
import com.app.group15.services.HttpServletRequestMock;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {
    private HttpServletRequestMock requestMock;
    private UserDaoMock userDaoMock;
    private UserRoleDaoMock userRoleDaoMock = new UserRoleDaoMock();
    private String sessionName;
    private String sessionValue;

    {
        requestMock = new HttpServletRequestMock();
        userDaoMock = new UserDaoMock();
    }


    @Test
    void setSessionTest() {
        String name = this.sessionName;
        String value = this.sessionValue;
        if (name != null && value != null) {
            requestMock.getSessionMock().setAttributeMock(name, value);
            assertEquals(requestMock.getSessionMock().getAttributeMock(name), value);
        } else {
            assertNull(requestMock.getSessionMock().getAttributeMock("BANNER_ID_SESSION"));
        }
    }

    void destroySessionTest() {
        this.sessionName = null;
        this.sessionValue = null;
        requestMock.getSessionMock().invalidateMock();
    }

    @Test
    void isUserSignedInTest() {
        String bannerId;
        bannerId = "B00843468";
        String sessionBannerId;

        this.sessionName = "BANNER_ID_SESSION";
        this.sessionValue = bannerId;

        setSessionTest();

        sessionBannerId = (String) requestMock.getSessionMock().getAttributeMock("BANNER_ID_SESSION");
        assertEquals(sessionBannerId, bannerId);

        destroySessionTest();

        sessionBannerId = (String) requestMock.getSessionMock().getAttributeMock("BANNER_ID_SESSION");
        assertNull(sessionBannerId);
    }

    @Test
    void getSessionUserTest() {
        String sessionBannerId;
        User user;

        this.sessionName = "BANNER_ID_SESSION";
        this.sessionValue = "B00843468";

        setSessionTest();

        sessionBannerId = (String) requestMock.getSessionMock().getAttributeMock("BANNER_ID_SESSION");
        user = userDaoMock.getUserByBannerIdMock(sessionBannerId);

        assertNotNull(user.getBannerId());

        destroySessionTest();

        sessionBannerId = (String) requestMock.getSessionMock().getAttributeMock("BANNER_ID_SESSION");
        assertNull(sessionBannerId);
    }

    @Test
    void getUserHomeTest() {
        String sessionBannerId;
        User user;

        this.sessionName = "BANNER_ID_SESSION";
        this.sessionValue = "B00843468";

        setSessionTest();

        sessionBannerId = (String) requestMock.getSessionMock().getAttributeMock("BANNER_ID_SESSION");
        Set<String> roles = userRoleDaoMock.getRolesByBannerIdMock(sessionBannerId);
        String redirect;

        if (roles.contains("ADMIN")) {
            redirect = "/admin/home";
        } else if (roles.contains("STUDENT") || roles.contains("TA")) {
            redirect = "/student/home";
        } else if (roles.contains("INSTRUCTOR")) {
            redirect = "/instructor/home";
        } else {
            redirect = "/user/home";
        }

        assertEquals(redirect, "/student/home");
        destroySessionTest();
    }
}
