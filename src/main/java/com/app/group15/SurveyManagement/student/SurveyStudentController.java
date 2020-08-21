package com.app.group15.SurveyManagement.student;

import com.app.group15.Config.AppConfig;
import com.app.group15.Config.ServiceConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.SurveyManagement.ISurveyManagementAbstractFactory;
import com.app.group15.SurveyManagement.ISurveyService;
import com.app.group15.SurveyManagement.Survey;
import com.app.group15.UserManagement.SessionManagement.IAuthorizationService;
import com.app.group15.UserManagement.SessionManagement.ISessionManagementAbstractFactory;
import com.app.group15.UserManagement.SessionManagement.ISessionService;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.GroupFormationToolLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.logging.Level;

@Controller
public class SurveyStudentController {

    private ISurveyManagementAbstractFactory surveyManagementAbstractFactory = AppConfig.getInstance().getSurveyManagementAbstractFactory();
	private ISessionManagementAbstractFactory sessionManagementAbstractFactory=AppConfig.getInstance().getSessionManagementAbstractFactory();
    private IAuthorizationService authorizationService = sessionManagementAbstractFactory.getAuthorizationService();
    private ISessionService sessionService = sessionManagementAbstractFactory.getSessionService();
    private ISurveyStudentService surveyStudentService = surveyManagementAbstractFactory.getSurveyStudentService();
    private ISurveyService surveyService = surveyManagementAbstractFactory.getSurveyService();

    @RequestMapping(value = "/student/survey", method = RequestMethod.GET)
    public ModelAndView getSurveyPage(HttpServletRequest request,
                                      @RequestParam(value = "courseId", required = true) int courseId) {
        authorizationService.setAllowedRoles(new String[]{"STUDENT", "TA"});
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
                    Survey survey = surveyService.getSurveyByCourseId(courseId);
                    modelAndView = new ModelAndView();
                    modelAndView.addObject("user", user);
                    modelAndView.addObject("survey", survey);
                    SurveyFormResponse surveyFormResponse = surveyStudentService.getSurveyQuestionWithOptions(courseId);
                    modelAndView.addObject("SurveyFromResponse", surveyFormResponse);
                    modelAndView.setViewName("student/survey");
                    return modelAndView;
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
        } catch (Exception e) {
            GroupFormationToolLogger.log(Level.FINEST, String.format("Error getting details for course ", courseId));
        }
        modelAndView = new ModelAndView("student/survey");
        return modelAndView;
    }

    @RequestMapping(value = "/student/survey/submit", method = RequestMethod.POST)
    public ModelAndView getResponse(HttpServletRequest request,
                                    @ModelAttribute(value = "SampleResponse") SurveyFormResponse surveyFormResponse) throws SQLException, AwsSecretsManagerException {
        authorizationService.setAllowedRoles(new String[]{"STUDENT", "TA"});
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    modelAndView = new ModelAndView();
                    User user = sessionService.getSessionUser(request);
                    surveyStudentService.submitResponse(user, surveyFormResponse);
                    modelAndView.addObject("user", user);
                    modelAndView.addObject("SurveyFromResponse", surveyFormResponse);
                    modelAndView.setViewName("student/surveySaved");
                    return modelAndView;
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
        } catch (Exception e) {
            GroupFormationToolLogger.log(Level.FINEST, "Error submitting survey");
        }
        modelAndView = new ModelAndView();
        return modelAndView;
    }
}
