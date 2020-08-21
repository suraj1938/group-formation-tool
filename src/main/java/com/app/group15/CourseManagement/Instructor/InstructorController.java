package com.app.group15.CourseManagement.Instructor;

import com.app.group15.Config.AppConfig;
import com.app.group15.Config.ServiceConfig;
import com.app.group15.CourseManagement.Course;
import com.app.group15.CourseManagement.CourseAbstractDao;
import com.app.group15.CourseManagement.ICourseManagementAbstractFactory;
import com.app.group15.CourseManagement.ICourseService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@Controller
public class InstructorController {


    private ICourseManagementAbstractFactory courseManagementAbstractFactory = AppConfig.getInstance().getCourseManagementAbstractFactory();
    private ICourseInstructorAbstractFactory courseInstructorAbstractFactory = AppConfig.getInstance().getCourseInstructorAbstractFactory();
	private ISessionManagementAbstractFactory sessionManagementAbstractFactory=AppConfig.getInstance().getSessionManagementAbstractFactory();
	private IUserManagementAbstractFactory userManagementAbstractFactory=AppConfig.getInstance().getUserManagementAbstractFactory();

	private IAuthorizationService authorizationService = sessionManagementAbstractFactory.getAuthorizationService();
    private CourseAbstractDao courseDao = courseManagementAbstractFactory.getCourseDao();
	private ISessionService sessionService = sessionManagementAbstractFactory.getSessionService();
    private ICourseService courseService = courseManagementAbstractFactory.getCourseService();
    private IInstructorService instructorService = courseInstructorAbstractFactory.getInstructorService();
    private IAssignTAService assignTaService = courseInstructorAbstractFactory.getAssignTAService();
	private IUserService userService = userManagementAbstractFactory.getUserService();

    @RequestMapping(value = "/instructor/home", method = RequestMethod.GET)
    public ModelAndView adminHome(HttpServletRequest request) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR"});
        ModelAndView modelAndView;
        try {
            if (sessionService.isUserSignedIn(request)) {
                if (authorizationService.isAuthorized(request)) {

                User userEntity = sessionService.getSessionUser(request);
                List<Course> courseEntities = instructorService.getCourseOfInstructor((userEntity.getId()));
                List<User> userEntitiesTA = instructorService.getAllCourseTA(courseEntities);

                modelAndView = new ModelAndView();
                modelAndView.setViewName("instructor/home");
                modelAndView.addObject("userEntity", userEntity);
                modelAndView.addObject("courseEntities", courseEntities);
                modelAndView.addObject("userEntitiesTA", userEntitiesTA);

                return modelAndView;
            } else {
                modelAndView = new ModelAndView("redirect:/login");
            }
        } else {
            modelAndView = new ModelAndView("redirect:/login");
        }
        return modelAndView;
        }catch(SQLException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
        	modelAndView = new ModelAndView("dbError");
        	return modelAndView;
        }
        catch (AwsSecretsManagerException e) {
			GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
			modelAndView = new ModelAndView("awsError");
			return modelAndView;
		}
    }

    @RequestMapping(value = "/instructor/assign-ta", method = RequestMethod.GET)
    public ModelAndView assignTAGET(HttpServletRequest request, @RequestParam String courseId) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR"});
        ModelAndView modelAndView;
        try {
        if (sessionService.isUserSignedIn(request)) {
            if (authorizationService.isAuthorized(request)) {

                User userEntity = sessionService.getSessionUser(request);
                Course courseEntity = (Course) courseDao.get(Integer.parseInt(courseId));

                modelAndView = new ModelAndView();
                modelAndView.setViewName("instructor/ta-assignment");
                modelAndView.addObject("courseId", courseId);
                modelAndView.addObject("userEntity", userEntity);
                modelAndView.addObject("courseEntity", courseEntity);

                return modelAndView;
            } else {
                modelAndView = new ModelAndView("redirect:/login");
            }
        } else {
            modelAndView = new ModelAndView("redirect:/login");
        }
        return modelAndView;
        }
        catch(SQLException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
        	modelAndView = new ModelAndView("dbError");
        	return modelAndView;
        }
        catch (AwsSecretsManagerException e) {
			GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
			modelAndView = new ModelAndView("awsError");
			return modelAndView;
		}
    }


    @RequestMapping(value = "/instructor/assign-ta", method = RequestMethod.POST)
    public ModelAndView assignTAPOST(HttpServletRequest request, @RequestParam(required = true, value = "bannerId") String bannerId,
                                     @RequestParam(required = true, value = "courseId") int courseId) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR"});
        ModelAndView modelAndView;
        try {
        if (sessionService.isUserSignedIn(request)) {
            if (authorizationService.isAuthorized(request)) {

                User userEntity = sessionService.getSessionUser(request);
                List<Course> courseEntities = instructorService.getCourseOfInstructor((userEntity.getId()));
                List<User> userEntitiesTA = instructorService.getAllCourseTA(courseEntities);
                Course courseEntity = (Course) courseDao.get(courseId);
                modelAndView = new ModelAndView();

                if (!instructorService.checkInstructorPermission(userEntity.getId(), courseId)) {
                    modelAndView.addObject("error_invalid_permission", true);
                } else {
                    modelAndView.addObject("error_invalid_permission", false);
                }

                if (userService.validateBannerID(bannerId)) {

                    if (assignTaService.performTAUpdate(bannerId, courseId)) {

                        modelAndView.addObject("error", false);
                        modelAndView.setViewName("redirect:/instructor/home");
                        return modelAndView;
                    } else {
                        modelAndView.addObject("error", true);
                        modelAndView.addObject("errorMessage", String.format("Student with Banner ID \"%s\" is enrolled in your course!", bannerId));
                    }
                } else {
                    modelAndView.addObject("error", true);
                    modelAndView.addObject("errorMessage", "Invalid Banner ID!");
                }

                modelAndView.setViewName("instructor/ta-assignment");
                modelAndView.addObject("courseId", courseId);
                modelAndView.addObject("userEntity", userEntity);
                modelAndView.addObject("courseEntity", courseEntity);
                modelAndView.addObject("userEntitiesTA", userEntitiesTA);
                return modelAndView;

            } else {
                modelAndView = new ModelAndView("redirect:/login");
            }
        } else {
            modelAndView = new ModelAndView("redirect:/login");
        }
        return modelAndView;
        }catch(SQLException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
        	modelAndView = new ModelAndView("dbError");
        	return modelAndView;
        }
        catch(AllowedRolesNotSetException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /roleError endpoint ");
        	modelAndView = new ModelAndView("roleError");
        	return modelAndView;
        }
        catch (AwsSecretsManagerException e) {
			GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
			modelAndView = new ModelAndView("awsError");
			return modelAndView;
		}
    }


    @RequestMapping(value = "/instructor/importCSV", method = RequestMethod.GET)
    public ModelAndView importCSV(HttpServletRequest request, @RequestParam(required = true, value = "courseId") int courseId) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR", "TA", "STUDENT"});
        ModelAndView modelAndView;
        try {
        if (sessionService.isUserSignedIn(request)) {
            if (authorizationService.isAuthorized(request)) {
                User user = sessionService.getSessionUser(request);
                GroupFormationToolLogger.log(Level.INFO, "User authorized 147");
                if (courseService.isUserCourseAdmin(courseId, user.getId())) {
                    GroupFormationToolLogger.log(Level.INFO, "User Course admin");
                    Course course = (Course) courseDao.get(courseId);
                    modelAndView = new ModelAndView();
                    modelAndView.setViewName("instructor/import_csv");
                    modelAndView.addObject("user", user);
                    modelAndView.addObject("course", course);
                } else {
                    GroupFormationToolLogger.log(Level.INFO, "User Not Course admin");
                    modelAndView = new ModelAndView();
                    modelAndView.setViewName("redirect:/login");
                }
            } else {
                modelAndView = new ModelAndView("redirect:/login");
            }
        } else {
            modelAndView = new ModelAndView("redirect:/login");
        }
        return modelAndView;
        }catch(SQLException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
        	modelAndView = new ModelAndView("dbError");
        	return modelAndView;
        }
        catch (AwsSecretsManagerException e) {
			GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
			modelAndView = new ModelAndView("awsError");
			return modelAndView;
		}
    }

    @RequestMapping(value = "/instructor/importCSV", method = RequestMethod.POST)
    public ModelAndView importCSV(HttpServletRequest request,
                                  RedirectAttributes redirectAttributes,
                                  @RequestParam(required = true, value = "courseId") int courseId,
                                  @RequestParam(required = true, value = "csvFile") MultipartFile csvFile) {
        authorizationService.setAllowedRoles(new String[]{"INSTRUCTOR", "TA"});
        ModelAndView modelAndView;
        try {
        if (sessionService.isUserSignedIn(request)) {
            if (authorizationService.isAuthorized(request)) {
                User user = sessionService.getSessionUser(request);
                if (courseService.isUserCourseAdmin(courseId, user.getId())) {
                    GroupFormationToolLogger.log(Level.INFO, "User authorized");
                    int insertCount = instructorService.addStudentsFromCSV(csvFile, courseId);
                    if (insertCount == -1) {
                        redirectAttributes.addFlashAttribute("error",
                                "Invalid file structure!");
                        modelAndView = new ModelAndView("redirect:/instructor/importCSV");
                    } else if (insertCount == 0) {
                        redirectAttributes.addFlashAttribute("error",
                                "No records to insert!");
                        modelAndView = new ModelAndView("redirect:/instructor/importCSV");
                    } else {
                        modelAndView = new ModelAndView();
                        redirectAttributes.addFlashAttribute("message",
                                "File uploaded successfully!");
                        modelAndView.setViewName("redirect:/instructor/home");
                    }
                } else {
                    GroupFormationToolLogger.log(Level.INFO, "User Not Course admin");
                    modelAndView = new ModelAndView();
                    modelAndView.setViewName("redirect:/login");
                }
            } else {
                modelAndView = new ModelAndView("redirect:/login");
            }
        } else {
            modelAndView = new ModelAndView("redirect:/login");
        }
        return modelAndView;
        }
        catch(SQLException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
        	modelAndView = new ModelAndView("dbError");
        	return modelAndView;
        }
        catch(AllowedRolesNotSetException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /roleError endpoint ");
        	modelAndView = new ModelAndView("roleError");
        	return modelAndView;
        }
        catch(AwsSecretsManagerException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
        	modelAndView = new ModelAndView("awsError");
        	return modelAndView;
        }
    }
}
