package com.app.group15.CourseManagement;


import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.SessionManagement.IAuthorizationService;
import com.app.group15.UserManagement.SessionManagement.ISessionManagementAbstractFactory;
import com.app.group15.UserManagement.SessionManagement.ISessionService;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.GroupFormationToolLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@Controller
public class GuestController {
    private ISessionManagementAbstractFactory sessionManagementAbstractFactory = AppConfig.getInstance().getSessionManagementAbstractFactory();
    private IAuthorizationService authorizationService = sessionManagementAbstractFactory.getAuthorizationService();
    private ICourseManagementAbstractFactory courseManagementAbstractFactory = AppConfig.getInstance().getCourseManagementAbstractFactory();
    private ISessionService sessionService = sessionManagementAbstractFactory.getSessionService();
    private ICourseService courseService = courseManagementAbstractFactory.getCourseService();

    @RequestMapping(value = "/user/home", method = RequestMethod.GET)
    public ModelAndView guestHome(HttpServletRequest request) {
        authorizationService.setAllowedRoles(new String[]{"GUEST"});
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
                    List<Course> courses = courseService.getCoursesList();
                    modelAndView = new ModelAndView();
                    modelAndView.setViewName("home");
                    modelAndView.addObject("user", user);
                    modelAndView.addObject("courses", courses);
                    return modelAndView;
                } else {
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging user out. Sending user to /login");
                    modelAndView = new ModelAndView("redirect:/login");
                }
            } else {
                GroupFormationToolLogger.log(Level.INFO, "No user Logged in, sending user to /login");
                modelAndView = new ModelAndView("redirect:/login");
            }
            return modelAndView;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /dbError endpoint ");
            modelAndView = new ModelAndView("dbError");
            return modelAndView;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /awsError endpoint ");
            modelAndView = new ModelAndView("awsError");
            return modelAndView;
        }
    }
}
