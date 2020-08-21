package com.app.group15.CourseManagement.Student;


import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.DatabaseManager;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

import static com.app.group15.Utility.DatabaseQueriesUtility.*;


@SuppressWarnings("rawtypes")
public class CourseStudentMapperDao extends CourseStudentMapperAbstractDao {


    @Override
    public int addStudentToACourse(int courseId, int studentId) throws SQLException, AwsSecretsManagerException {
        String query = ADD_STUDENT_TO_COURSE;
        int courseStudentMapperId = 0;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setInt(1, courseId);
                statement.setInt(2, studentId);
                statement.executeUpdate();
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.first()) {
                        courseStudentMapperId = result.getInt(1);
                        GroupFormationToolLogger.log(Level.FINEST, "Student:" + studentId + "Added to Course :" + courseId);
                    }
                }
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
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        return courseStudentMapperId;
    }

    @Override
    public void deletByCourseId(int courseId) throws SQLException, AwsSecretsManagerException {
        String query = DELETE_BY_COURSE_ID_FROM_COURSE_STUDENT_MAPPER;
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            statement.setInt(1, courseId);
            statement.executeUpdate();
            connection.commit();
            GroupFormationToolLogger.log(Level.FINEST, "Course Deleted :" + courseId);
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ArrayList<CourseStudentMapper> getAll() throws SQLException, AwsSecretsManagerException {
        String query = SELECT_ALL_FROM_COURSE_STUDENT_MAPPER;
        ArrayList<CourseStudentMapper> allList = new ArrayList<CourseStudentMapper>();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                CourseStudentMapper entity = (CourseStudentMapper) AppConfig.getInstance().getCourseStudentAbstractFactory().getCourseStudentMapperModel();
                entity.setId(result.getInt("id"));
                entity.setCourseId(result.getInt("course_id"));
                entity.setStudentId(result.getInt("student_id"));
                allList.add(entity);
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return allList;
    }

    @Override
    public ArrayList<Integer> getCourseIdsOfAStudent(int studentId) throws SQLException, AwsSecretsManagerException {
        String query = GET_COURSE_ID_OF_STUDENT;
        ArrayList<Integer> courseIds = new ArrayList<Integer>();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    courseIds.add(Integer.valueOf(result.getInt("course_id")));
                }
            }
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return courseIds;
    }


}
