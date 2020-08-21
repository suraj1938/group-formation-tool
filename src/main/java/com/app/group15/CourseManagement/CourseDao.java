package com.app.group15.CourseManagement;


import com.app.group15.Config.AppConfig;
import com.app.group15.CourseManagement.Instructor.CourseInstructorMapperAbstractDao;
import com.app.group15.CourseManagement.Instructor.CourseInstructorMapperDao;
import com.app.group15.CourseManagement.Instructor.ICourseInstructorMapperInjector;
import com.app.group15.CourseManagement.Student.CourseStudentMapperAbstractDao;
import com.app.group15.CourseManagement.Student.CourseStudentMapperDao;
import com.app.group15.CourseManagement.Student.ICourseStudentMapperDaoInjector;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.DatabaseManager;
import com.app.group15.Persistence.Persistence;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

import static com.app.group15.Utility.DatabaseQueriesUtility.*;


@SuppressWarnings("rawtypes")
public class CourseDao extends CourseAbstractDao
        implements ICourseInstructorMapperInjector, ICourseStudentMapperDaoInjector {

    private CourseInstructorMapperAbstractDao courseInstructorMapperDao;
    private CourseStudentMapperAbstractDao courseStudentMapperDao;

    @Override
    public Course get(int id) throws SQLException, AwsSecretsManagerException {
        String query = GET_COURSE_FROM_ID;
        Course course = (Course) AppConfig.getInstance().getCourseManagementAbstractFactory().getCourseModel();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    course.setId(result.getInt("id"));
                    course.setName(result.getString("name"));
                }
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return course;
    }

    @Override
    public ArrayList<Course> getAll() throws SQLException, AwsSecretsManagerException {
        String query = GET_ALL_COURSES;
        ArrayList<Course> coursesList = new ArrayList<Course>();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Course course = (Course) AppConfig.getInstance().getCourseManagementAbstractFactory().getCourseModel();
                course.setId(result.getInt("id"));
                course.setName(result.getString("name"));
                coursesList.add(course);
            }

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        return coursesList;
    }

    @Override
    public int save(Persistence course) throws SQLException, AwsSecretsManagerException {
        Course courseEntity = (Course) course;
        String query = SAVE_COURSE;
        int courseId = 0;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, courseEntity.getName());
                statement.executeUpdate();
                connection.commit();
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.first()) {
                        courseId = result.getInt(1);
                        GroupFormationToolLogger.log(Level.SEVERE, "Course :" + courseId + " is Saved");
                    }
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                    throw e;
                }
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        return courseId;

    }

    @Override
    public void update(Persistence course, int id) throws SQLException, AwsSecretsManagerException {
        Course courseEntity = (Course) course;
        String query = UPDATE_COURSE;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                statement.setString(1, courseEntity.getName());
                statement.setInt(2, id);
                statement.executeUpdate();
                connection.commit();
                GroupFormationToolLogger.log(Level.SEVERE, "Course :" + id + " is updated");
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                    throw e;
                }
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            }

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public void delete(int id) throws SQLException, AwsSecretsManagerException {
        String query = DELETE_COURSE;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                courseInstructorMapperDao.deleteByCourseId(id);
                courseStudentMapperDao.deletByCourseId(id);
                statement.setInt(1, id);
                statement.executeUpdate();
                connection.commit();
                GroupFormationToolLogger.log(Level.SEVERE, "Course :" + id + " is deleted");
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                    throw e;
                }
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public void injectCourseInstructorMapperDao(CourseInstructorMapperDao courseInstructorMapperDao) {
        this.courseInstructorMapperDao = courseInstructorMapperDao;

    }

    @Override
    public void injectCourseStudentMapperDao(CourseStudentMapperDao courseStudentMapperDao) {
        this.courseStudentMapperDao = courseStudentMapperDao;

    }

}

