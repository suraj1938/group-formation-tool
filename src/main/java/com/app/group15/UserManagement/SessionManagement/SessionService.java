package com.app.group15.UserManagement.SessionManagement;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.*;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Set;

public class SessionService implements ISessionService {
    public boolean isUserSignedIn(HttpServletRequest request) {
        String sessionBannerId = (String) request.getSession().getAttribute("BANNER_ID_SESSION");
        return sessionBannerId != null;
    }

    public User getSessionUser(HttpServletRequest request) throws SQLException, AwsSecretsManagerException {
        UserDao userDao = new UserDaoInjectorService().getUserDao();
        String sessionBannerId = (String) request.getSession().getAttribute("BANNER_ID_SESSION");
        return userDao.getUserByBannerId(sessionBannerId);
    }

    public void destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    public void setSession(HttpServletRequest request, String name, String value) {
        request.getSession().setAttribute(name, value);
    }

    public String getUserHome(HttpServletRequest request) throws SQLException, AwsSecretsManagerException {
        String sessionBannerId = (String) request.getSession().getAttribute("BANNER_ID_SESSION");
        UserRoleDao userRoleDao = new UserRoleDaoInjectorService().getUserRoleDao();
        Set<String> roles = userRoleDao.getRolesByBannerId(sessionBannerId);
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
        return redirect;
    }
}
