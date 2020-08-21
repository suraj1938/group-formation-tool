package com.app.group15.CourseManagement.Instructor;


import com.app.group15.CourseManagement.CourseAbstractDao;
import com.app.group15.CourseManagement.CourseService;
import com.app.group15.CourseManagement.ICourseService;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.IUserService;
import com.app.group15.UserManagement.User;
import com.app.group15.UserManagement.UserAbstractDao;
import com.app.group15.UserManagement.UserRoleAbstractDao;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.logging.Level;

public class AssignTAService implements IAssignTAService, IAssignTaServiceInjector {

    private UserAbstractDao userDao;
    private CourseAbstractDao courseDao;
    private UserRoleAbstractDao userRoleDao;
    private CourseInstructorMapperAbstractDao courseInstructorMapperDao;
    private IInstructorService instructorService;
    private IUserService userService;
    private ICourseService courseService;


    @Override
    public boolean performTAUpdate(String bannerId, int courseId) throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(bannerId) && ServiceUtility.isValidInt(courseId)) {
            User userEntity = userDao.getUserByBannerId(bannerId);
            if (userService.validateBannerID(bannerId) && courseService.validateCourseID(courseId)) {
                if (instructorService.validateUserToAddAsTa(userEntity, courseId)) {
                    instructorService.addOrUpdateStudentRole(userEntity, "TA");
                    GroupFormationToolLogger.log(Level.FINEST, "Student " + userEntity.getFirstName() + ", now has TA role");
                    courseInstructorMapperDao.addTaToACourse(courseId, userEntity.getId());
                    GroupFormationToolLogger.log(Level.FINEST, "Student added as TA to CourseID :" + courseId);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "Invalid BannerID or CourseID");
        }
        return false;
    }


    @Override
    public void injectUserDao(UserAbstractDao userDao) {
        if (ServiceUtility.isNotNull(userDao)) {
            this.userDao = userDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "UserDao injection issue in AssignTAService");
        }
    }

    @Override
    public void injectCourseDao(CourseAbstractDao courseDao) {
        if (ServiceUtility.isNotNull(courseDao)) {
            this.courseDao = courseDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "CourseDao injection issue in AssignTAService");
        }
    }

    @Override
    public void injectUserRoleDao(UserRoleAbstractDao userRoleDao) {
        if (ServiceUtility.isNotNull(userRoleDao)) {
            this.userRoleDao = userRoleDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "UserRoleDao injection issue in AssignTAService");
        }
    }

    @Override
    public void injectCourseInstructorMapper(CourseInstructorMapperAbstractDao courseInstructorMapperDao) {
        if (ServiceUtility.isNotNull(courseInstructorMapperDao)) {
            this.courseInstructorMapperDao = courseInstructorMapperDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "CourseInstructorMapperDao injection issue in AssignTAService");
        }
    }

    @Override
    public void injectInstructorService(IInstructorService instructorService) {
        if (ServiceUtility.isNotNull(instructorService)) {
            this.instructorService = instructorService;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "InstructorService injection issue in AssignTAService");
        }

    }

    @Override
    public void injectUserService(IUserService userService) {
        if (ServiceUtility.isNotNull(userService)) {
            this.userService = userService;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "UserService injection issue in AssignTAService");
        }

    }

    @Override
    public void injectCourseService(CourseService courseService) {
        if (ServiceUtility.isNotNull(courseService)) {
            this.courseService = courseService;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "CourseService injection issue in AssignTAService");
        }
    }


}
