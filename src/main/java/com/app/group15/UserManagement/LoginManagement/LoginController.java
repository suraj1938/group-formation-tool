package com.app.group15.UserManagement.LoginManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.Config.ServiceConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.SessionManagement.ISessionManagementAbstractFactory;
import com.app.group15.UserManagement.SessionManagement.ISessionService;
import com.app.group15.Utility.GroupFormationToolLogger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

	private ILoginManagementAbstractFactory loginManagementAbstractFactory= AppConfig.getInstance().getLoginManagementAbstractFactory();
	private ISessionManagementAbstractFactory sessionManagementAbstractFactory=AppConfig.getInstance().getSessionManagementAbstractFactory();

	private ISessionService sessionService = sessionManagementAbstractFactory.getSessionService();
    private ILoginService loginService = loginManagementAbstractFactory.getLoginService();

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(required = false, value = "bannerId") String bannerId, @RequestParam(required = false, value = "password") String password, HttpServletRequest request) {

    	try {
        if (loginService.validateLogin(bannerId, password)) {
            sessionService.setSession(request, "BANNER_ID_SESSION", bannerId);
            String redirect = "redirect:" + sessionService.getUserHome(request);
            return new ModelAndView(redirect);
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            modelAndView.addObject("error", true);
            return modelAndView;
        }
    	}
    	catch(SQLException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
        	ModelAndView modelAndView = new ModelAndView("dbError");
        	return modelAndView;
        }
    	catch (AwsSecretsManagerException e) {
			GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint in login ");
			ModelAndView modelAndViewResponse = new ModelAndView("awsError");
			return modelAndViewResponse;
		}
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
    	try {
        if (sessionService.isUserSignedIn(request)) {
            String redirect = "redirect:" + sessionService.getUserHome(request);

            return new ModelAndView(redirect);
        }
        return new ModelAndView("login");
    	}
    	catch(SQLException e) {
        	GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
        	ModelAndView modelAndView = new ModelAndView("dbError");
        	return modelAndView;
        }
    	catch (AwsSecretsManagerException e) {
			GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
			ModelAndView modelAndViewResponse = new ModelAndView("awsError");
			return modelAndViewResponse;
		}

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        sessionService.destroySession(request);
        return "redirect:/login";
    }
}
