package com.app.group15.CourseManagement.Student;

import com.app.group15.Config.AppConfig;
import com.app.group15.Config.ServiceConfig;
import com.app.group15.CourseManagement.Course;
import com.app.group15.CourseManagement.ICourseService;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.SurveyManagement.ISurveyManagementAbstractFactory;
import com.app.group15.SurveyManagement.student.ISurveyStudentService;
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
public class StudentController {
    private ISurveyManagementAbstractFactory surveyManagementAbstractFactory = AppConfig.getInstance().getSurveyManagementAbstractFactory();
    private ISessionManagementAbstractFactory sessionManagementAbstractFactory = AppConfig.getInstance().getSessionManagementAbstractFactory();
    private IAuthorizationService authorizationService = ServiceConfig.getInstance().getAuthorizationService();
    private ISurveyStudentService surveyStudentService = surveyManagementAbstractFactory.getSurveyStudentService();
    private ISessionService sessionService = sessionManagementAbstractFactory.getSessionService();
    private ICourseService courseService = AppConfig.getInstance().getCourseManagementAbstractFactory().getCourseService();

    @RequestMapping(value = "/student/home", method = RequestMethod.GET)
    public ModelAndView studentHome(HttpServletRequest request) {
        authorizationService.setAllowedRoles(new String[]{"STUDENT", "TA"});
        ModelAndView modelAndViewResponse;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
                    List<Course> coursesAsStudent = courseService.getStudentCourses(user.getId());
                    List<Boolean> surveyAppeared = surveyStudentService.validateIfUserHasSubmittedSurveyBefore(coursesAsStudent, user.getId());
                    List<User> coursesAsStudentInstructors = courseService.getAllCourseInstructors(coursesAsStudent);
                    Course courseAsTa = courseService.getStudentCourseAsTa(user.getId());
                    User courseAsTaInstructor = courseService.getCourseInstructor(courseAsTa.getId());
                    modelAndViewResponse = new ModelAndView();
                    modelAndViewResponse.setViewName("student/home");
                    modelAndViewResponse.addObject("user", user);
                    modelAndViewResponse.addObject("coursesAsStudent", coursesAsStudent);
                    modelAndViewResponse.addObject("surveyAppeared", surveyAppeared);
                    modelAndViewResponse.addObject("coursesAsStudentInstructor", coursesAsStudentInstructors);
                    modelAndViewResponse.addObject("courseAsTa", courseAsTa);
                    modelAndViewResponse.addObject("courseAsTaInstructor", courseAsTaInstructor);
                } else {
                    modelAndViewResponse = new ModelAndView("redirect:/login");
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging user out.");
                }
            } else {
                modelAndViewResponse = new ModelAndView("redirect:/login");
                GroupFormationToolLogger.log(Level.INFO, "No user logged in");
            }
            return modelAndViewResponse;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /dbError endpoint ");
            modelAndViewResponse = new ModelAndView("dbError");
            return modelAndViewResponse;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /awsError endpoint ");
            modelAndViewResponse = new ModelAndView("awsError");
            return modelAndViewResponse;
        }
    }

    @RequestMapping(value = "/student/courseInfo", method = RequestMethod.GET)
    public ModelAndView studentCourse(HttpServletRequest request,
                                      @RequestParam(required = true, value = "courseId") int courseId) {
        authorizationService.setAllowedRoles(new String[]{"STUDENT", "TA"});
        ModelAndView modelAndViewResponse;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
                    Course course = courseService.getCourseDetails(courseId);
                    User courseInstructor = courseService.getCourseInstructor(courseId);
                    if (course.getName() != null) {
                        modelAndViewResponse = new ModelAndView();
                        modelAndViewResponse.setViewName("student/courseInfo");
                        modelAndViewResponse.addObject("course", course);
                        modelAndViewResponse.addObject("courseInstructor", courseInstructor);
                        modelAndViewResponse.addObject("user", user);
                    } else {
                        modelAndViewResponse = new ModelAndView("redirect:/student/home");
                        GroupFormationToolLogger.log(Level.SEVERE, "Course Error");
                    }
                } else {
                    modelAndViewResponse = new ModelAndView("redirect:/login");
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging user out.");
                }
            } else {
                modelAndViewResponse = new ModelAndView("redirect:/login");
                GroupFormationToolLogger.log(Level.INFO, "No user logged in");
            }
            return modelAndViewResponse;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /dbError endpoint ");
            modelAndViewResponse = new ModelAndView("dbError");
            return modelAndViewResponse;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /awsError endpoint ");
            modelAndViewResponse = new ModelAndView("awsError");
            return modelAndViewResponse;
        }
    }
}
