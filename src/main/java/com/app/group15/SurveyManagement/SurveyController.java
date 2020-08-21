package com.app.group15.SurveyManagement;


import com.app.group15.Config.AppConfig;
import com.app.group15.CourseManagement.Course;
import com.app.group15.CourseManagement.CourseAbstractDao;
import com.app.group15.CourseManagement.ICourseManagementAbstractFactory;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.QuestionManager.Question;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Controller
public class SurveyController {

    private ISurveyManagementAbstractFactory surveyManagementAbstractFactory = AppConfig.getInstance().getSurveyManagementAbstractFactory();
    private ISessionManagementAbstractFactory sessionManagementAbstractFactory = AppConfig.getInstance().getSessionManagementAbstractFactory();
    private IAuthorizationService authorizationService = sessionManagementAbstractFactory.getAuthorizationService();
    private ICourseManagementAbstractFactory courseManagementAbstractFactory = AppConfig.getInstance().getCourseManagementAbstractFactory();
    private CourseAbstractDao courseDao = courseManagementAbstractFactory.getCourseDao();
    private ISessionService sessionService = sessionManagementAbstractFactory.getSessionService();
    private ISurveyService surveyService = surveyManagementAbstractFactory.getSurveyService();

    @RequestMapping(value = "/instructor/survey", method = RequestMethod.GET)
    public ModelAndView manageSurveyGET(HttpServletRequest request,
                                        @RequestParam(value = "courseId", required = true) String courseId,
                                        @RequestParam(value = "show", required = false) String showField) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR", "TA"});
        ArrayList<Question> allQuestionsOfInstructor = null;
        List<Question> questionList = new ArrayList<>();
        ModelAndView modelAndView;
        Survey survey;
        boolean showLt = false, showGt = false;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
                    allQuestionsOfInstructor = (ArrayList<Question>) surveyService.getRemainingQuestionsForSurvey(Integer.parseInt(courseId), user.getId());
                    GroupFormationToolLogger.log(Level.INFO, "Successfully got the remaining question list of survey");
                    Course courseEntity = (Course) courseDao.get(Integer.parseInt(courseId));
                    questionList = surveyService.getSurveyQuestionByCourseID(courseEntity.getId());
                    GroupFormationToolLogger.log(Level.INFO, "Successfully got the question list of course ");
                    modelAndView = new ModelAndView();
                    if (showField != null && showField.equals("lt")) {
                        showLt = true;
                    } else if (showField != null && showField.equals("gt")) {
                        showGt = true;
                    } else {
                        showLt = false;
                        showGt = false;
                    }
                    survey = surveyService.getSurveyByCourseId(Integer.parseInt(courseId));
                    modelAndView.setViewName("instructor/survey");
                    modelAndView.addObject("survey", survey);
                    modelAndView.addObject("allQuestionsOfInstructor", allQuestionsOfInstructor);
                    modelAndView.addObject("courseId", courseId);
                    modelAndView.addObject("userEntity", user);
                    modelAndView.addObject("courseEntity", courseEntity);
                    modelAndView.addObject("questionList", questionList);
                    modelAndView.addObject("showLt", showLt);
                    modelAndView.addObject("showGt", showGt);

                    return modelAndView;
                } else {
                    modelAndView = new ModelAndView("redirect:/login");
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging out user");
                }
            } else {
                modelAndView = new ModelAndView("redirect:/login");
                GroupFormationToolLogger.log(Level.INFO, "No user logged in");
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

    @RequestMapping(value = "instructor/survey/addQuestion", method = RequestMethod.POST)
    public ModelAndView manageSurvey(HttpServletRequest request,
                                     @RequestParam(value = "courseId", required = true) int courseId,
                                     @RequestParam(value = "questionId", required = true) int questionId,
                                     @RequestParam(value = "rule", required = true) String rule,
                                     @RequestParam(value = "ruleValue", required = false) String ruleVal) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR", "TA"});
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    int ruleValue;
                    if (ruleVal != null) {
                        ruleValue = Integer.parseInt(ruleVal);
                    } else {
                        ruleValue = 0;
                    }
                    int mapperId = surveyService.addQuestionToSurvey(courseId, questionId, rule, ruleValue);
                    GroupFormationToolLogger.log(Level.FINEST, String.format("Question added to survey with id %d", mapperId));
                    modelAndView = new ModelAndView(String.format("redirect:/instructor/survey?courseId=%d", courseId));
                    return modelAndView;
                } else {
                    modelAndView = new ModelAndView("redirect:/login");
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging out user");
                }
            } else {
                modelAndView = new ModelAndView("redirect:/login");
                GroupFormationToolLogger.log(Level.INFO, "No user logged in");
            }
            return modelAndView;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /dbError endpoint", e);
            modelAndView = new ModelAndView("dbError");
            return modelAndView;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /awsError endpoint", e);
            modelAndView = new ModelAndView("awsError");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/instructor/survey/deleteQuestion", method = RequestMethod.POST)
    public ModelAndView deleteQuestion(HttpServletRequest request,
                                       @RequestParam(value = "courseId", required = true) int courseId,
                                       @RequestParam(value = "questionId", required = true) int questionId) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR", "TA"});
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    surveyService.deleteSurveyQuestion(questionId, courseId);
                    GroupFormationToolLogger.log(Level.FINEST, String.format("Question with id %d deleted from survey with id", questionId));
                    modelAndView = new ModelAndView(String.format("redirect:/instructor/survey?courseId=%d", courseId));
                    return modelAndView;
                } else {
                    modelAndView = new ModelAndView("redirect:/login");
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging out user");
                }
            } else {
                modelAndView = new ModelAndView("redirect:/login");
                GroupFormationToolLogger.log(Level.INFO, "No user logged in");
            }
            return modelAndView;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /dbError endpoint ", e);
            modelAndView = new ModelAndView("dbError");
            return modelAndView;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /awsError endpoint ", e);
            modelAndView = new ModelAndView("awsError");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/instructor/survey/publish", method = RequestMethod.POST)
    public ModelAndView publishSurvey(HttpServletRequest request,
                                      @RequestParam(value = "courseId", required = true) int courseId) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR", "TA"});
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    surveyService.publishSurvey(courseId);
                    GroupFormationToolLogger.log(Level.FINEST, String.format("Survey of course with course id %d published!", courseId));
                    modelAndView = new ModelAndView(String.format("redirect:/instructor/survey?courseId=%d", courseId));
                    return modelAndView;
                } else {
                    modelAndView = new ModelAndView("redirect:/login");
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging out user");
                }
            } else {
                modelAndView = new ModelAndView("redirect:/login");
                GroupFormationToolLogger.log(Level.INFO, "No user logged in");
            }
            return modelAndView;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /dbError endpoint ", e);
            modelAndView = new ModelAndView("dbError");
            return modelAndView;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /awsError endpoint ", e);
            modelAndView = new ModelAndView("awsError");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/instructor/survey/unPublish", method = RequestMethod.POST)
    public ModelAndView unPublishSurvey(HttpServletRequest request,
                                        @RequestParam(value = "courseId", required = true) int courseId) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR", "TA"});
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    surveyService.unPublishSurvey(courseId);
                    GroupFormationToolLogger.log(Level.FINEST, String.format("Survey of course with course id %d published!", courseId));
                    modelAndView = new ModelAndView(String.format("redirect:/instructor/survey?courseId=%d", courseId));
                    return modelAndView;
                } else {
                    modelAndView = new ModelAndView("redirect:/login");
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging out user");
                }
            } else {
                GroupFormationToolLogger.log(Level.INFO, "No user logged in");
                modelAndView = new ModelAndView("redirect:/login");
            }
            return modelAndView;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /dbError endpoint ", e);
            modelAndView = new ModelAndView("dbError");
            return modelAndView;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.SEVERE, " Redirecting to /awsError endpoint ", e);
            modelAndView = new ModelAndView("awsError");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/instructor/surveyResponse", method = RequestMethod.GET)
    public ModelAndView surveyResponse(HttpServletRequest request,
                                       @RequestParam(value = "courseId", required = true) String courseId) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR"});
        List<SurveyUserResponse> surveyUserResponses;
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {
                    User user = sessionService.getSessionUser(request);
                    surveyUserResponses = surveyService.getSurveyResponse(courseId);
                    modelAndView = new ModelAndView();
                    modelAndView.setViewName("instructor/survey-response");
                    modelAndView.addObject("surveyUserResponses", surveyUserResponses);
                    modelAndView.addObject("courseId", courseId);
                    modelAndView.addObject("user", user);
                    return modelAndView;
                } else {
                    modelAndView = new ModelAndView("redirect:/login");
                    GroupFormationToolLogger.log(Level.INFO, "Unauthorized! Logging out user");
                }
            } else {
                modelAndView = new ModelAndView("redirect:/login");
                GroupFormationToolLogger.log(Level.INFO, "No user logged in");
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
