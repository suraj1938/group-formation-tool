package com.app.group15.UserManagement.SessionManagement;

import com.app.group15.UserManagement.UserRoleDaoMock;
import com.app.group15.services.HttpServletRequestMock;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AuthorizationServiceTest {
    private HttpServletRequestMock requestMock;
    private UserRoleDaoMock userRoleDaoMock = new UserRoleDaoMock();
    private Set<String> allowedRoles;

    {
        allowedRoles = new HashSet<>();
        requestMock = new HttpServletRequestMock();
    }

    private void setAllowedRoles(String[] args) {
        this.allowedRoles.clear();
        this.allowedRoles.addAll(Arrays.asList(args));
    }

    @Test
    void isAuthorized() {
        Set<String> roles;
        String bannerId;
        Set<String> intersection;
        int intersectionSize;
        setAllowedRoles(new String[]{"STUDENT"});
        requestMock.getSessionMock().setAttributeMock("BANNER_ID_SESSION", "B00843468");
        bannerId = (String) requestMock.getSessionMock().getAttributeMock("BANNER_ID_SESSION");
        intersection = new HashSet<>(this.allowedRoles);
        roles = userRoleDaoMock.getRolesByBannerIdMock(bannerId);
        intersection.retainAll(roles);
        intersectionSize = intersection.size();
        assertNotEquals(intersectionSize, 0);

        setAllowedRoles(new String[]{"INSTRUCTOR"});
        requestMock.getSessionMock().setAttributeMock("BANNER_ID_SESSION", "B00843468");
        bannerId = (String) requestMock.getSessionMock().getAttributeMock("BANNER_ID_SESSION");
        intersection = new HashSet<>(this.allowedRoles);
        roles = userRoleDaoMock.getRolesByBannerIdMock(bannerId);
        intersection.retainAll(roles);
        intersectionSize = intersection.size();
        assertEquals(intersectionSize, 0);


    }
}
