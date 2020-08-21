package com.app.group15.CourseManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.CourseManagement.Instructor.CourseInstructorMapperAbstractDao;
import com.app.group15.CourseManagement.Student.CourseStudentMapperAbstractDao;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.Persistence;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CourseService implements ICourseService, ICourseServiceInjector {

    private ICourseManagementAbstractFactory courseManagementAbstractFactory = AppConfig.getInstance().getCourseManagementAbstractFactory();
    private CourseAbstractDao courseDao;
    private CourseInstructorMapperAbstractDao courseInstructorMapperDao;
    private CourseStudentMapperAbstractDao courseStudentMapperDao;
    private String invalidInput = "Invalid input";

    @Override
    public List<Course> getCoursesList() throws SQLException, AwsSecretsManagerException {
        return courseDao.getAll();
    }

    @Override
    public Course getCourseDetails(int id) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(id)) {
            return (Course) courseDao.get(id);
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return null;
        }
    }

    @Override
    public User getCourseInstructor(int id) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(id)) {
            User user = courseInstructorMapperDao.getCourseInstructor(id);
            return user;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return null;
        }
    }

    @Override
    public List<User> getAllCourseInstructors(List<Course> courses) {
        if (ServiceUtility.isNotNull(courses)) {
            List<User> userInstructors = new ArrayList<>();
            courses.forEach(course -> {
                try {
                    userInstructors.add(getCourseInstructor(course.getId()));
                } catch (SQLException | AwsSecretsManagerException e) {
                    GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                }
            });
            return userInstructors;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return null;
        }
    }

    @Override
    public void addOrUpdateInstructor(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(courseId) && ServiceUtility.isValidInt(instructorId)) {
            courseInstructorMapperDao.addInstructorToACourse(courseId, instructorId);
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
    }

    @Override
    public int addCourse(String courseName) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isNotNull(courseName)) {
            Course course = (Course) courseManagementAbstractFactory.getCourseModel();
            course.setName(courseName);
            int courseId = courseDao.save(course);
            return courseId;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return -1;
    }

    @Override
    public void deleteCourse(int courseId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(courseId)) {
            courseInstructorMapperDao.deleteByCourseId(courseId);
            courseDao.delete(courseId);
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
    }

    @Override
    public boolean isUserCourseAdmin(int courseId, int userId) throws SQLException, AwsSecretsManagerException {

        if (ServiceUtility.isValidInt(courseId) && ServiceUtility.isValidInt(userId)) {
            User userTa = courseInstructorMapperDao.getCourseTA(courseId);
            User userInstructor = courseInstructorMapperDao.getCourseInstructor(courseId);
            GroupFormationToolLogger.log(Level.INFO, String.valueOf(courseId));
            GroupFormationToolLogger.log(Level.INFO, userTa.getBannerId() + " " + userInstructor.getBannerId());
            return userId == userTa.getId() || userId == userInstructor.getId();
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return false;
        }
    }

    @Override
    public List<Course> getStudentCourses(int studentId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(studentId)) {
            ArrayList<Integer> courseIdsOfAStudent = courseStudentMapperDao.getCourseIdsOfAStudent(studentId);
            ArrayList<Course> courses = new ArrayList<>();
            for (Integer courseId : courseIdsOfAStudent) {
                courses.add(getCourseDetails(courseId));
            }
            return courses;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return null;
    }

    @Override
    public Course getStudentCourseAsTa(int taId) throws AwsSecretsManagerException, SQLException {
        if (ServiceUtility.isValidInt(taId)) {
            return courseInstructorMapperDao.getCourseByTa(taId);
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
        }
        return null;
    }

    @Override
    public void injectCourseDao(CourseAbstractDao courseDao) {
        if (ServiceUtility.isNotNull(courseDao)) {
            this.courseDao = courseDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "CourseDao injection issue in CourseService");
        }

    }

    @Override
    public void injectCourseInstructorMapper(CourseInstructorMapperAbstractDao courseInstructorMapperDao) {
        if (ServiceUtility.isNotNull(courseInstructorMapperDao)) {
            this.courseInstructorMapperDao = courseInstructorMapperDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "courseInstructorMapperDao injection issue in CourseService");
        }
    }

    @Override
    public void injectCourseStudentMapper(CourseStudentMapperAbstractDao courseStudentMapperDao) {

        if (ServiceUtility.isNotNull(courseStudentMapperDao)) {
            this.courseStudentMapperDao = courseStudentMapperDao;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "courseStudentMapperDao injection issue in CourseService");
        }
    }

    @Override
    public boolean validateCourseID(int courseId) throws SQLException, AwsSecretsManagerException {
        if (ServiceUtility.isValidInt(courseId)) {
            return courseDao.get(courseId) != null;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, invalidInput);
            return false;
        }
    }

    @Override
    public Persistence get(int courseId) throws SQLException, AwsSecretsManagerException {
        return courseDao.get(courseId);
    }


}
