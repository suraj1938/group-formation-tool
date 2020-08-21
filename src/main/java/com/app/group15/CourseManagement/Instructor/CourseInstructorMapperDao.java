package com.app.group15.CourseManagement.Instructor;


import com.app.group15.Config.AppConfig;
import com.app.group15.CourseManagement.Course;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.DatabaseManager;
import com.app.group15.UserManagement.User;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

import static com.app.group15.Utility.DatabaseQueriesUtility.*;


@SuppressWarnings("rawtypes")
public class CourseInstructorMapperDao extends CourseInstructorMapperAbstractDao {

    @Override
    public ArrayList<CourseInstructorMapper> getAll() throws SQLException, AwsSecretsManagerException {
        String query = SELECT_ALL_COURSE_INSTRUCTOR_MAPPER;
        ArrayList<CourseInstructorMapper> allList = new ArrayList<CourseInstructorMapper>();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                CourseInstructorMapper entity = (CourseInstructorMapper) AppConfig.getInstance().getCourseInstructorAbstractFactory().getCourseInstructorMapperModel();
                entity.setId(result.getInt("id"));
                entity.setCourseId(result.getInt("course_id"));
                entity.setInstructorId(result.getInt("instructor_id"));
                entity.setTaId(result.getInt("ta_id"));
                allList.add(entity);
            }

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return allList;
    }

    @Override
    public User getCourseInstructor(int id) throws SQLException, AwsSecretsManagerException {
        String query = GET_COURSE_INSTRUCTOR;
        User userEntity = (User) AppConfig.getInstance().getUserManagementAbstractFactory().getUserModel();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    GroupFormationToolLogger.log(Level.INFO, result.getString("first_name"));
                    userEntity.setFirstName(result.getString("first_name"));
                    userEntity.setLastName(result.getString("last_name"));
                    userEntity.setId(result.getInt("instructor_id"));
                }

            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return userEntity;
    }

    @Override
    public void deleteByCourseId(int courseId) throws SQLException, AwsSecretsManagerException {
        String query = DELETE_BY_COURSE_ID;
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            statement.setInt(1, courseId);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    protected boolean doesCourseIdExistInThisMapper(int courseId) throws SQLException, AwsSecretsManagerException {
        String query = CHECK_COURSE_ID_EXISTS;
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, courseId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {

            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return false;
    }

    @Override
    protected void addInstructorForCourseWithTa(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException {
        String query = ADD_INSTRUCTOR_FOR_COURSE_WITH_TA;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                statement.setInt(1, instructorId);
                statement.setInt(2, courseId);
                statement.executeUpdate();
                connection.commit();
            } catch (Exception e) {
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
    protected void addTaForCourseWithInstructor(int courseId, int taId) throws SQLException, AwsSecretsManagerException {
        String query = ADD_TA_FOR_COURSE_WITH_INSTRUCTOR;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                statement.setInt(1, taId);
                statement.setInt(2, courseId);
                statement.executeUpdate();
                connection.commit();
            } catch (Exception e) {
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
    public void addInstructorToACourse(int courseId, int instructorId) throws SQLException, AwsSecretsManagerException {
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            if (doesCourseIdExistInThisMapper(courseId)) {
                addInstructorForCourseWithTa(courseId, instructorId);

            } else {
                String query = ADD_INSTRUCTOR_TO_COURSE;

                try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, courseId);
                    statement.setInt(2, instructorId);
                    statement.executeUpdate();
                    connection.commit();
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
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void addTaToACourse(int courseId, int taId) throws SQLException, AwsSecretsManagerException {
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            if (doesCourseIdExistInThisMapper(courseId)) {
                addTaForCourseWithInstructor(courseId, taId);

            } else {
                String query = ADD_TA_TO_COURSE;

                try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, courseId);
                    statement.setInt(2, taId);
                    statement.executeUpdate();
                    connection.commit();
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

            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }


    }

    @Override
    public Course getCourseByTa(int taId) throws AwsSecretsManagerException, SQLException {
        String query = GET_COURSE_BY_TA;
        Course course = (Course) AppConfig.getInstance().getCourseManagementAbstractFactory().getCourseModel();

        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, taId);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        course.setId(result.getInt("course_id"));
                        course.setName(result.getString("name"));
                    }
                }
            } catch (SQLException e) {
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return course;
    }

    @Override
    public ArrayList<Course> getCoursesByInstructor(int id) throws AwsSecretsManagerException, SQLException {
        String query = GET_COURSE_BY_INSTRUCTOR;
        ArrayList<Course> arrayListCourse = new ArrayList<Course>();

        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        Course courseEntity = (Course) AppConfig.getInstance().getCourseManagementAbstractFactory().getCourseModel();
                        courseEntity.setId(result.getInt("course_id"));
                        courseEntity.setName(result.getString("name"));
                        arrayListCourse.add(courseEntity);
                    }
                }
            } catch (SQLException e) {
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return arrayListCourse;
    }

    @Override
    public User getCourseTA(int id) throws SQLException, AwsSecretsManagerException {
        String query = GET_COURSE_TA;
        User userEntity = (User) AppConfig.getInstance().getUserManagementAbstractFactory().getUserModel();

        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        userEntity.setFirstName(result.getString("first_name"));
                        userEntity.setLastName(result.getString("last_name"));
                        userEntity.setId(result.getInt("ta_id"));
                    }
                }
            } catch (SQLException e) {
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return userEntity;
    }

    @Override
    public ArrayList<Course> getCourseByInstructor(int id) throws SQLException, AwsSecretsManagerException {
        String query = GET_COURSE_BY_INSTRUCTOR;
        ArrayList<Course> arrayListCourse = new ArrayList<Course>();

        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        Course courseEntity = (Course) AppConfig.getInstance().getCourseManagementAbstractFactory().getCourseModel();
                        courseEntity.setId(result.getInt("course_id"));
                        courseEntity.setName(result.getString("name"));
                        arrayListCourse.add(courseEntity);
                    }
                }
            } catch (SQLException e) {
                GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
                throw e;
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return arrayListCourse;
    }


}
