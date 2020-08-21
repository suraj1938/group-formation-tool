package com.app.group15.CourseManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.IUserManagementAbstractFactory;
import com.app.group15.UserManagement.IUserService;
import com.app.group15.UserManagement.SessionManagement.IAuthorizationService;
import com.app.group15.UserManagement.SessionManagement.ISessionManagementAbstractFactory;
import com.app.group15.UserManagement.SessionManagement.ISessionService;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.GroupFormationToolLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@Controller
public class AdminController {

    private ICourseManagementAbstractFactory courseManagementAbstractFactory = AppConfig.getInstance().getCourseManagementAbstractFactory();
    private ICourseService courseService = courseManagementAbstractFactory.getCourseService();

    private ISessionManagementAbstractFactory sessionManagementAbstractFactory = AppConfig.getInstance().getSessionManagementAbstractFactory();
    private IUserManagementAbstractFactory userManagementAbstractFactory = AppConfig.getInstance().getUserManagementAbstractFactory();
    private IAuthorizationService authorizationService = sessionManagementAbstractFactory.getAuthorizationService();
    private ISessionService sessionService = sessionManagementAbstractFactory.getSessionService();
    private IUserService userService = userManagementAbstractFactory.getUserService();


    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView adminHome(HttpServletRequest request) {

        ModelAndView modelAndViewResponse;
        try {

            authorizationService.setAllowedRoles(new String[]{"ADMIN"});
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
                    List<Course> courses = courseService.getCoursesList();
                    modelAndViewResponse = new ModelAndView();
                    modelAndViewResponse.setViewName("admin/home");
                    modelAndViewResponse.addObject("user", user);
                    modelAndViewResponse.addObject("courses", courses);
                    return modelAndViewResponse;
                } else {
                    modelAndViewResponse = new ModelAndView("redirect:/login");
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging user out");
                }
            } else {
                modelAndViewResponse = new ModelAndView("redirect:/login");
            }
            return modelAndViewResponse;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
            modelAndViewResponse = new ModelAndView("dbError");
            return modelAndViewResponse;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
            modelAndViewResponse = new ModelAndView("awsError");
            return modelAndViewResponse;
        }
    }

    @RequestMapping(value = "/admin/courses", method = RequestMethod.GET)
    public ModelAndView adminCourses(HttpServletRequest request,
                                     @RequestParam(required = false, value = "feedback") String feedback) {
        authorizationService.setAllowedRoles(new String[]{"ADMIN"});

        ModelAndView modelAndViewResponse;
        try {

            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {

                    User user = sessionService.getSessionUser(request);
                    List<Course> courses = courseService.getCoursesList();
                    List<User> userInstructors = courseService.getAllCourseInstructors(courses);

                    modelAndViewResponse = new ModelAndView();
                    modelAndViewResponse.setViewName("admin/courses");
                    modelAndViewResponse.addObject("user", user);
                    modelAndViewResponse.addObject("courses", courses);
                    modelAndViewResponse.addObject("instructorEntities", userInstructors);
                    modelAndViewResponse.addObject("feedback", feedback);

                    return modelAndViewResponse;
                } else {
                    modelAndViewResponse = new ModelAndView("redirect:/login");
                }
            } else {
                modelAndViewResponse = new ModelAndView("redirect:/login");
            }
            return modelAndViewResponse;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
            modelAndViewResponse = new ModelAndView("dbError");
            return modelAndViewResponse;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
            modelAndViewResponse = new ModelAndView("awsError");
            return modelAndViewResponse;
        }
    }

    @RequestMapping(value = "/admin/courses/add", method = RequestMethod.GET)
    public ModelAndView adminCourseAdd(HttpServletRequest request) {
        authorizationService.setAllowedRoles(new String[]{"ADMIN"});
        ModelAndView modelAndViewResponse;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
                    List<User> allUsers = userService.getAllUsers();
                    modelAndViewResponse = new ModelAndView();
                    modelAndViewResponse.setViewName("admin/courseAdd");
                    modelAndViewResponse.addObject("user", user);
                    modelAndViewResponse.addObject("allUsers", allUsers);

                    return modelAndViewResponse;
                } else {
                    modelAndViewResponse = new ModelAndView("redirect:/login");
                }
            } else {
                modelAndViewResponse = new ModelAndView("redirect:/login");
            }
            return modelAndViewResponse;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
            modelAndViewResponse = new ModelAndView("dbError");
            return modelAndViewResponse;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
            modelAndViewResponse = new ModelAndView("awsError");
            return modelAndViewResponse;
        }

    }

    @RequestMapping(value = "/admin/courses/add", method = RequestMethod.POST)
    public ModelAndView adminCourseAdd(HttpServletRequest request,
                                       @RequestParam(required = true, value = "courseName") String courseName,
                                       @RequestParam(required = false, value = "instructorId") int instructorId) {
        authorizationService.setAllowedRoles(new String[]{"ADMIN"});
        ModelAndView modelAndViewResponse;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    int courseId = courseService.addCourse(courseName);
                    if (instructorId != -1) {
                        courseService.addOrUpdateInstructor(courseId, instructorId);
                    }
                    userService.updateUserRole(instructorId, "INSTRUCTOR");
                    modelAndViewResponse = new ModelAndView("redirect:/admin/courses?feedback=courseAdded");
                } else {
                    modelAndViewResponse = new ModelAndView("redirect:/login");
                }
            } else {
                modelAndViewResponse = new ModelAndView("redirect:/login");
            }
            return modelAndViewResponse;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
            modelAndViewResponse = new ModelAndView("dbError");
            return modelAndViewResponse;
        } catch (AllowedRolesNotSetException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /roleError endpoint ");
            modelAndViewResponse = new ModelAndView("roleError");
            return modelAndViewResponse;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
            modelAndViewResponse = new ModelAndView("awsError");
            return modelAndViewResponse;
        }
    }

    @RequestMapping(value = "/admin/courses/edit", method = RequestMethod.GET)
    public ModelAndView adminCourseEdit(HttpServletRequest request,
                                        @RequestParam(required = true, value = "courseId") int courseId) {
        authorizationService.setAllowedRoles(new String[]{"ADMIN"});
        ModelAndView modelAndViewResponse;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
                    Course course = courseService.getCourseDetails(courseId);
                    User courseInstructor = courseService.getCourseInstructor(courseId);
                    List<User> allUsers = userService.getAllUsers();
                    modelAndViewResponse = new ModelAndView();
                    modelAndViewResponse.setViewName("admin/courseEdit");
                    modelAndViewResponse.addObject("user", user);
                    modelAndViewResponse.addObject("course", course);
                    modelAndViewResponse.addObject("courseInstructor", courseInstructor);
                    modelAndViewResponse.addObject("allUsers", allUsers);
                    return modelAndViewResponse;
                } else {
                    modelAndViewResponse = new ModelAndView("redirect:/login");
                }
            } else {
                modelAndViewResponse = new ModelAndView("redirect:/login");
            }
            return modelAndViewResponse;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
            modelAndViewResponse = new ModelAndView("dbError");
            return modelAndViewResponse;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
            modelAndViewResponse = new ModelAndView("awsError");
            return modelAndViewResponse;
        }
    }

    @RequestMapping(value = "/admin/courses/edit", method = RequestMethod.POST)
    public ModelAndView adminCourseEdit(HttpServletRequest request,
                                        @RequestParam(required = true, value = "courseId") int courseId,
                                        @RequestParam(required = true, value = "instructorId") int instructorId) {
        authorizationService.setAllowedRoles(new String[]{"ADMIN"});
        ModelAndView modelAndViewResponse;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    courseService.addOrUpdateInstructor(courseId, instructorId);
                    modelAndViewResponse = new ModelAndView("redirect:/admin/courses?feedback=instructorAdded");
                } else {
                    modelAndViewResponse = new ModelAndView("redirect:/login");
                }
            } else {
                modelAndViewResponse = new ModelAndView("redirect:/login");
            }
            return modelAndViewResponse;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
            modelAndViewResponse = new ModelAndView("dbError");
            return modelAndViewResponse;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
            modelAndViewResponse = new ModelAndView("awsError");
            return modelAndViewResponse;
        }
    }

    @RequestMapping(value = "/admin/courses/delete", method = RequestMethod.GET)
    public ModelAndView adminCourseDelete(HttpServletRequest request,
                                          @RequestParam(required = true, value = "courseId") int courseId) {
        authorizationService.setAllowedRoles(new String[]{"ADMIN"});
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    courseService.deleteCourse(courseId);
                    modelAndView = new ModelAndView("redirect:/admin/courses?feedback=courseDeleted");
                } else {
                    modelAndView = new ModelAndView("redirect:/login");
                }
            } else {
                modelAndView = new ModelAndView("redirect:/login");
            }
            return modelAndView;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
            modelAndView = new ModelAndView("dbError");
            return modelAndView;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
            modelAndView = new ModelAndView("awsError");
            return modelAndView;
        }
    }

}
