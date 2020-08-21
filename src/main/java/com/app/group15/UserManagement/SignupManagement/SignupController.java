package com.app.group15.UserManagement.SignupManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.Config.ServiceConfig;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.PasswordPolicyManagement.IPasswordPolicyAbstractFactory;
import com.app.group15.PasswordPolicyManagement.IPasswordPolicyService;
import com.app.group15.PasswordPolicyManagement.PasswordPolicyValidationResult;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.GroupFormationToolLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.logging.Level;

@Controller
public class SignupController {

    private IPasswordPolicyAbstractFactory passwordPolicyAbstractFactory = AppConfig.getInstance().getPasswordPolicyAbstractFactory();
	private ISignupManagementAbstractFactory signupManagementAbstractFactory= AppConfig.getInstance().getSignupManagementAbstractFactory();
	private ISignupService signupService = signupManagementAbstractFactory.getSignupService();
	private IPasswordPolicyService passwordPolicyService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", false);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signup(@ModelAttribute User user, @RequestParam("cPassword") String cPassword) {
        String bannerId = user.getBannerId();
        try {
            boolean response = signupService.checkUserExists(bannerId);
            passwordPolicyService = passwordPolicyAbstractFactory.getPasswordPolicyService();
            PasswordPolicyValidationResult result = passwordPolicyService.validatePassword(user.getPassword(), -1);
            if (response) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("signup");
                modelAndView.addObject("error", true);
                modelAndView.addObject("bannerId_error", "Banner Id already registered!");
                return modelAndView;
            } else if (!cPassword.equals(user.getPassword())) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("signup");
                modelAndView.addObject("error", true);
                modelAndView.addObject("password_error", "Password and confirm password did not match!");
                return modelAndView;
            } else if (!result.getResult()) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("signup");
                modelAndView.addObject("error", true);
                modelAndView.addObject("password_error", result.isMessage());
                return modelAndView;
            } else {
                int userId = signupService.createUser(user, "GUEST");
                return new ModelAndView("redirect:login");
            }

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /dbError endpoint ");
            ModelAndView modelAndView = new ModelAndView("dbError");
            return modelAndView;
        } catch (AllowedRolesNotSetException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /roleError endpoint ");
            ModelAndView modelAndViewResponse = new ModelAndView("roleError");
            return modelAndViewResponse;
        } catch (AwsSecretsManagerException e) {
            GroupFormationToolLogger.log(Level.INFO, " Redirecting to /awsError endpoint ");
            ModelAndView modelAndViewResponse = new ModelAndView("awsError");
            return modelAndViewResponse;
        }
    }
}
