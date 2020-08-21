package com.app.group15.CourseManagement.Instructor;

import com.app.group15.Config.AppConfig;
import com.app.group15.CourseManagement.Course;
import com.app.group15.CourseManagement.CourseAbstractDao;
import com.app.group15.CourseManagement.Student.CourseStudentMapperAbstractDao;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.UserManagement.ForgetPassword.ForgetPasswordUtility;
import com.app.group15.UserManagement.User;
import com.app.group15.UserManagement.UserAbstractDao;
import com.app.group15.UserManagement.UserRoleAbstractDao;
import com.app.group15.Utility.FileUtility;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class InstructorService implements IInstructorService, IInstructorServiceInjector {

    private CourseInstructorMapperAbstractDao courseInstructorMapperDao;
    private CourseStudentMapperAbstractDao courseStudentMapperDao;
    private UserAbstractDao userDao;
    private UserRoleAbstractDao userRoleDao;
    private CourseAbstractDao courseDao;
    private String invalidInput = "Invalid input";


    @Override
    public List<Course> getCourseOfInstructor(int instructorId) throws AwsSecretsManagerException, SQLException {
        if (ServiceUtility.isValidInt(instructorId)) {
            List<Course> arrayListCourses = courseInstructorMapperDao.getCoursesByInstructor(instructorId);
            return arrayListCourses;
        } else {

            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return null;
    }

    @Override
    public User getCourseTA(int userId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(userId)) {
            User userEntity = courseInstructorMapperDao.getCourseTA(userId);
            return userEntity;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return null;
    }

    @Override
    public List<User> getAllCourseTA(List<Course> courseEntities) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(courseEntities)) {
            List<User> userEntitiesTa = new ArrayList<>();
            courseEntities.forEach(courseEntity -> {
                try {
                    userEntitiesTa.add(getCourseTA(courseEntity.getId()));
                } catch (SQLException | AwsSecretsManagerException e) {
                    GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                }
            });
            return userEntitiesTa;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return null;
    }

    @Override
    public boolean validateUserToAddAsTa(User user, int courseId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(user) && ServiceUtility.isValidInt(courseId)) {
            boolean valid;
            ArrayList<Integer> courseIdsOfAStudent = courseStudentMapperDao.getCourseIdsOfAStudent(user.getId());
            valid = !courseIdsOfAStudent.contains(courseId);
            return valid;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return false;
    }

    @Override
    public void addOrUpdateStudentRole(User user, String role) throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(user) && ServiceUtility.isNotNull(role)) {
            Set<String> userRoles = userRoleDao.getRolesByBannerId(user.getBannerId());
            if (userRoles.contains(role)) {
                GroupFormationToolLogger.log(Level.INFO, String.format("User with banner id %s is already a student", user.getBannerId()));
            } else {
                userRoleDao.addRole(user.getId(), role);
            }
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
    }

    @Override
    public int addStudentsFromCSV(MultipartFile file, int courseId) throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(file) && ServiceUtility.isNotNull(courseId)) {

            String[] cols;
            cols = new String[]{"name", "email", "banner_id"};
            ArrayList<HashMap<String, String>> data = FileUtility.readCSV(file, cols);
            String name, bannerId, email;
            Course course = (Course) courseDao.get(courseId);
            String emailSubject = "Update on your courses";
            int insertCount = 0;
            User user;
            if (data.size() > 0 && data.get(0).get("error") != null) {
                return -1;
            }
            if (data.size() == 0) {
                return 0;
            } else {
                for (HashMap<String, String> dataRow : data) {
                    String emailBody = String.format("Welcome to the course %s.", course.getName());
                    name = dataRow.get("name");
                    bannerId = dataRow.get("banner_id");
                    email = dataRow.get("email");
                    user = userDao.getUserByBannerId(bannerId);
                    GroupFormationToolLogger.log(Level.INFO, "Getting user by banner id");
                    if (user.getBannerId() != null) {
                        generateNewUserAndToCourse(courseId, email, emailSubject, user, emailBody);
                    } else {
                        addExistingUserToCourse(courseId, name, bannerId, email, emailSubject, user, emailBody);
                    }
                    insertCount++;
                }
            }
            return insertCount;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return -1;
    }

    @Override
    public void injectUserDao(UserAbstractDao userDao) {
        if (ServiceUtility.isNotNull(userDao)) {
            this.userDao = userDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "UserDao injection issue in InstructorService");
        }

    }

    @Override
    public void injectUserRoleDao(UserRoleAbstractDao userRoleDao) {

        if (ServiceUtility.isNotNull(userRoleDao)) {
            this.userRoleDao = userRoleDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "UserRoleDao injection issue in InstructorService");
        }
    }

    @Override
    public void injectCourseDao(CourseAbstractDao courseDao) {
        if (ServiceUtility.isNotNull(courseDao)) {
            this.courseDao = courseDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "CourseDao injection issue in InstructorService");
        }
    }

    @Override
    public void injectCourseInstructorMapper(CourseInstructorMapperAbstractDao courseInstructorMapperDao) {
        if (ServiceUtility.isNotNull(courseInstructorMapperDao)) {
            this.courseInstructorMapperDao = courseInstructorMapperDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "CourseInstructorMapperDao injection issue in InstructorService");
        }
    }

    @Override
    public void injectCourseStudentMapper(CourseStudentMapperAbstractDao courseStudentMapperDao) {
        if (ServiceUtility.isNotNull(courseStudentMapperDao)) {
            this.courseStudentMapperDao = courseStudentMapperDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "CourseStudentMapperDao injection issue in InstructorService");
        }
    }

    @Override
    public boolean checkInstructorPermission(int instructorId, int courseId) throws AwsSecretsManagerException, SQLException {
        if (ServiceUtility.isValidInt(instructorId) && ServiceUtility.isValidInt(courseId)) {
            boolean returnVar = false;
            List<Course> courseEntitiesList = courseInstructorMapperDao.getCoursesByInstructor(instructorId);

            for (Course courseEntity : courseEntitiesList) {
                if (courseEntity.getId() == courseId) {
                    returnVar = true;
                    break;
                }
            }
            return returnVar;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return false;
    }

    private void addExistingUserToCourse(int courseId, String name, String bannerId, String email, String emailSubject, User user, String emailBody) throws AllowedRolesNotSetException, SQLException, AwsSecretsManagerException {
        GroupFormationToolLogger.log(Level.INFO, String.format("%s not registered in system!", user.getBannerId()));
        String[] firstLast = name.split(" ");
        user.setFirstName(firstLast[0]);
        user.setLastName(firstLast[1]);
        user.setBannerId(bannerId);
        user.setEmail(email);
        String tempPassword = ForgetPasswordUtility.generateForgotPasswordToken();
        user.setPassword(tempPassword);
        int userId = userDao.saveUser(user, "STUDENT");
        user.setId(userId);
        GroupFormationToolLogger.log(Level.INFO, String.format("%s registered with id %d!", user.getBannerId(), userId));
        int courseStudentMapperId = courseStudentMapperDao.addStudentToACourse(courseId, userId);
        GroupFormationToolLogger.log(Level.INFO, String.format("%d is CourseStudentMapperId for %s!", courseStudentMapperId, user.getBannerId()));
        addOrUpdateStudentRole(user, "STUDENT");
        userDao.updateUserRole(user.getId(), "STUDENT");
        emailBody += String.format("Your temporary password is %s.\nPlease change the password once by clicking on Forgot password on login page", tempPassword);
        AppConfig.getInstance().getEmailNotifier().sendMessage(email, emailSubject, emailBody);
    }

    private void generateNewUserAndToCourse(int courseId, String email, String emailSubject, User user, String emailBody) throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException {
        GroupFormationToolLogger.log(Level.INFO, String.format("%s already exists!", user.getBannerId()));
        if (validateUserToAddAsStudent(user)) {
            ArrayList<Integer> courseIdsOfAStudent = courseStudentMapperDao.getCourseIdsOfAStudent(user.getId());
            GroupFormationToolLogger.log(Level.INFO, "Checking if student is already enrolled");
            if (courseIdsOfAStudent.contains(courseId)) {
                GroupFormationToolLogger.log(Level.INFO, String.format("%s is already registered to the course id %d", user.getFirstName(), courseId));
            } else {
                GroupFormationToolLogger.log(Level.INFO, String.format("%s not enrolled!", user.getBannerId()));
                int courseStudentMapperId = courseStudentMapperDao.addStudentToACourse(courseId, user.getId());
                GroupFormationToolLogger.log(Level.INFO, String.format("%d is CourseStudentMapperId for %s!", courseStudentMapperId, user.getBannerId()));
                addOrUpdateStudentRole(user, "STUDENT");
                AppConfig.getInstance().getEmailNotifier().sendMessage(email, emailSubject, emailBody);
            }
        } else {
            GroupFormationToolLogger.log(Level.INFO, String.format("%s is an Instructor", user.getFirstName()));
        }
    }


    public boolean validateUserToAddAsStudent(User user) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(user)) {
            Set<String> userRoles = userRoleDao.getRolesByBannerId(user.getBannerId());
            boolean valid;

            if (userRoles.contains("INSTRUCTOR")) {
                GroupFormationToolLogger.log(Level.INFO, String.format("User with banner id %s is INSTRUCTOR", user.getBannerId()));
                valid = false;
            } else if (userRoles.contains("TA")) {
                Course course = courseInstructorMapperDao.getCourseByTa(user.getId());
                if (course.getName() != null) {
                    GroupFormationToolLogger.log(Level.INFO, String.format("User with banner id %s is TA for this course (course id %d)", user.getBannerId(), course.getId()));
                    valid = false;
                } else {
                    valid = true;
                }
            } else {
                valid = true;
            }

            return valid;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return false;
    }

}
