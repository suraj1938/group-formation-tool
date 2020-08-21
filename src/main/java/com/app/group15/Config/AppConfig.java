package com.app.group15.Config;

import com.app.group15.CourseManagement.CourseManagementConcreteFactory;
import com.app.group15.CourseManagement.ICourseManagementAbstractFactory;
import com.app.group15.CourseManagement.Instructor.CourseInstructorConcreteFactory;
import com.app.group15.CourseManagement.Instructor.CourseInstructorMapperDaoInjectorService;
import com.app.group15.CourseManagement.Instructor.ICourseInstructorAbstractFactory;
import com.app.group15.CourseManagement.Student.CourseStudentConcreteFactory;
import com.app.group15.CourseManagement.Student.CourseStudentMapperDaoInjectorService;
import com.app.group15.CourseManagement.Student.ICourseStudentAbstractFactory;
import com.app.group15.GroupFormationManagement.GroupFormationAlgorithmConcreteFactory;
import com.app.group15.GroupFormationManagement.IGroupFormationAlgorithmAbstractFactory;
import com.app.group15.NotificationManagement.EmailNotifierImpl;
import com.app.group15.PasswordPolicyManagement.IPasswordPolicyAbstractFactory;
import com.app.group15.PasswordPolicyManagement.PasswordPolicyConcreteFactory;
import com.app.group15.QuestionManager.IQuestionManagerAbstractFactory;
import com.app.group15.QuestionManager.QuestionManagerConcreteFactory;
import com.app.group15.SurveyManagement.ISurveyManagementAbstractFactory;
import com.app.group15.SurveyManagement.SurveyManagementConcreteFactory;
import com.app.group15.UserManagement.ForgetPassword.ForgetPasswordConcreteFactory;
import com.app.group15.UserManagement.ForgetPassword.IForgetPasswordAbstractFactory;
import com.app.group15.UserManagement.*;
import com.app.group15.UserManagement.LoginManagement.ILoginManagementAbstractFactory;
import com.app.group15.UserManagement.LoginManagement.LoginManagementConcreteFactory;
import com.app.group15.UserManagement.SessionManagement.ISessionManagementAbstractFactory;
import com.app.group15.UserManagement.SessionManagement.SessionManagementConcreteFactory;
import com.app.group15.UserManagement.SignupManagement.ISignupManagementAbstractFactory;
import com.app.group15.UserManagement.SignupManagement.SignupManagementConcreteFactory;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class AppConfig {

    private static AppConfig singletonAppConfig = null;

    private EmailNotifierImpl emailNotifier;
    private GroupFormationToolLogger groupFormationToolLogger;
    private Properties properties;
    private CourseStudentMapperDaoInjectorService courseStudentMapperDaoInjectorService;
    private CourseInstructorMapperDaoInjectorService courseInstructorMapperDaoInjectorService;
    private UserRoleDaoInjectorService userRoleDaoInjectorService;
    private UserAbstractDao userDao;


    private ICourseManagementAbstractFactory courseManagementAbstractFactory;
    private ICourseInstructorAbstractFactory courseInstructorAbstractFactory;
    private ICourseStudentAbstractFactory courseStudentAbstractFactory;


    // Abstract Factory
    private IQuestionManagerAbstractFactory questionManagerAbstractFactory;
    private IGroupFormationAlgorithmAbstractFactory groupAlgorithmAbstractFactory;
    private ISurveyManagementAbstractFactory surveyManagementAbstractFactory;
    private IPasswordPolicyAbstractFactory passwordPolicyAbstractFactory;
    private IUserManagementAbstractFactory userManagementAbstractFactory;
    private IForgetPasswordAbstractFactory forgetPasswordAbstractFactory;
    private ILoginManagementAbstractFactory loginManagementAbstractFactory;
    private ISessionManagementAbstractFactory sessionManagementAbstractFactory;
    private ISignupManagementAbstractFactory signupManagementAbstractFactory;


    private AppConfig() {

        properties = new Properties();
        String propertyFilePath = "src/main/resources/application.properties";
        try (FileInputStream in = new FileInputStream(propertyFilePath)) {
            properties.load(in);
        } catch (IOException ex) {
            GroupFormationToolLogger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        emailNotifier = new EmailNotifierImpl();
        groupFormationToolLogger = new GroupFormationToolLogger();

        courseInstructorMapperDaoInjectorService = new CourseInstructorMapperDaoInjectorService();
        courseStudentMapperDaoInjectorService = new CourseStudentMapperDaoInjectorService();
        userRoleDaoInjectorService = new UserRoleDaoInjectorService();
        userDao = new UserDaoInjectorService().getUserDao();

        courseManagementAbstractFactory = CourseManagementConcreteFactory.getInstance();
        courseInstructorAbstractFactory = CourseInstructorConcreteFactory.getInstance();
        courseStudentAbstractFactory = CourseStudentConcreteFactory.getInstance();
        //ABSTRACT FACTORY
        questionManagerAbstractFactory = QuestionManagerConcreteFactory.getInstance();
        groupAlgorithmAbstractFactory = GroupFormationAlgorithmConcreteFactory.getInstance();
        surveyManagementAbstractFactory = SurveyManagementConcreteFactory.getInstance();
        passwordPolicyAbstractFactory = PasswordPolicyConcreteFactory.getInstance();
        userManagementAbstractFactory=UserManagementConcreteFactory.getInstance();
        forgetPasswordAbstractFactory= ForgetPasswordConcreteFactory.getInstance();
        loginManagementAbstractFactory= LoginManagementConcreteFactory.getInstance();
        sessionManagementAbstractFactory= SessionManagementConcreteFactory.getInstance();
        signupManagementAbstractFactory= SignupManagementConcreteFactory.getInstance();
    }

    public static AppConfig getSingletonAppConfig() {
        return singletonAppConfig;
    }

    public static void setSingletonAppConfig(AppConfig singletonAppConfig) {
        AppConfig.singletonAppConfig = singletonAppConfig;
    }

    public static AppConfig getInstance() {
        if (null == AppConfig.getUniqueInstance()) {
            singletonAppConfig = new AppConfig();
        }
        return AppConfig.singletonAppConfig;
    }

    private static AppConfig getUniqueInstance() {
        return singletonAppConfig;
    }

    public UserAbstractDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public EmailNotifierImpl getEmailNotifier() {
        return emailNotifier;
    }

    public void setEmailNotifier(EmailNotifierImpl emailNotifier) {
        this.emailNotifier = emailNotifier;
    }

    public GroupFormationToolLogger getGroupFormationToolLogger() {
        return groupFormationToolLogger;
    }

    public void setGroupFormationToolLogger(GroupFormationToolLogger groupFormationToolLogger) {
        this.groupFormationToolLogger = groupFormationToolLogger;
    }

    public CourseStudentMapperDaoInjectorService getCourseStudentMapperDaoInjectorService() {
        return courseStudentMapperDaoInjectorService;
    }

    public void setCourseStudentMapperDaoInjectorService(
            CourseStudentMapperDaoInjectorService courseStudentMapperDaoInjectorService) {
        this.courseStudentMapperDaoInjectorService = courseStudentMapperDaoInjectorService;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public CourseInstructorMapperDaoInjectorService getCourseInstructorMapperDaoInjectorService() {
        return courseInstructorMapperDaoInjectorService;
    }

    public void setCourseInstructorMapperDaoInjectorService(
            CourseInstructorMapperDaoInjectorService courseInstructorMapperDaoInjectorService) {
        this.courseInstructorMapperDaoInjectorService = courseInstructorMapperDaoInjectorService;
    }

    public UserRoleDaoInjectorService getUserRoleDaoInjectorService() {
        return userRoleDaoInjectorService;
    }

    public void setUserRoleDaoInjectorService(UserRoleDaoInjectorService userRoleDaoInjectorService) {
        this.userRoleDaoInjectorService = userRoleDaoInjectorService;
    }

    public IQuestionManagerAbstractFactory getQuestionManagerAbstractFactory() {
        return questionManagerAbstractFactory;
    }

    public IGroupFormationAlgorithmAbstractFactory getGroupAlgorithmAbstractFactory() {
        return groupAlgorithmAbstractFactory;
    }

    public ISurveyManagementAbstractFactory getSurveyManagementAbstractFactory() {
        return surveyManagementAbstractFactory;
    }

    public IPasswordPolicyAbstractFactory getPasswordPolicyAbstractFactory() {
        return passwordPolicyAbstractFactory;

    }

    public ICourseManagementAbstractFactory getCourseManagementAbstractFactory() {
        return courseManagementAbstractFactory;
    }

    public ICourseInstructorAbstractFactory getCourseInstructorAbstractFactory() {
        return courseInstructorAbstractFactory;
    }

    public ICourseStudentAbstractFactory getCourseStudentAbstractFactory() {
        return courseStudentAbstractFactory;
    }

	public IUserManagementAbstractFactory getUserManagementAbstractFactory() {
		return userManagementAbstractFactory;
	}

	public IForgetPasswordAbstractFactory getForgetPasswordAbstractFactory() {
		return forgetPasswordAbstractFactory;
	}

	public ILoginManagementAbstractFactory getLoginManagementAbstractFactory() {
		return loginManagementAbstractFactory;
	}

	public ISessionManagementAbstractFactory getSessionManagementAbstractFactory() {
		return sessionManagementAbstractFactory;
	}

	public ISignupManagementAbstractFactory getSignupManagementAbstractFactory() {
		return signupManagementAbstractFactory;
	}
}
