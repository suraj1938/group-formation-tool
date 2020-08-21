package com.app.group15.UserManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AllowedRolesNotSetException;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.DatabaseManager;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import static com.app.group15.Utility.DatabaseQueriesUtility.*;


@SuppressWarnings("rawtypes")
public class UserRoleDao extends UserRoleAbstractDao {

	@Override
    public UserRoles get(int id) throws SQLException, AwsSecretsManagerException {
        String query = GET_USER_ROLE;
        UserRoles roleEntity = (UserRoles) AppConfig.getInstance().getUserManagementAbstractFactory().getUserRoleModel();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {

                    roleEntity.setId(result.getInt("id"));
                    roleEntity.setRole(result.getString("role"));

                }
            }

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        return roleEntity;

    }

    @Override
    public int getRoleId(String role) throws SQLException, AwsSecretsManagerException {
        String query = GET_ROLE_ID;
        int roleId = 0;
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, role);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {

                    roleId = result.getInt("id");

                }
            }

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
        return roleId;

    }

    @Override
    public Set<String> getRolesByBannerId(String bannerId) throws SQLException, AwsSecretsManagerException {
        String query = GET_ROLES_BY_BANNER_ID;
        Set<String> roles = new HashSet<String>();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bannerId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    String role = result.getString("role");
                    roles.add(role);
                }
            }
        } catch (SQLException e) {

            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        return roles;

    }

    @Override
    public void addRole(int userId, String role) throws SQLException, AllowedRolesNotSetException, AwsSecretsManagerException {
        String query =ADD_ROLE;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                statement.setInt(1, userId);
                if (role.equals(UserRole.ADMIN.getRole())) {
                    throw new AllowedRolesNotSetException("Admin User Cannot be created");
                }
                statement.setInt(2, getRoleId(role));
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

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public void updateRole(int userId, String role) throws AllowedRolesNotSetException, SQLException, AwsSecretsManagerException {
        String query = UPDATE_ROLE;
        try (Connection connection = DatabaseManager.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                if (role.equals(UserRole.ADMIN.getRole())) {
                    throw new AllowedRolesNotSetException("Admin User Cannot be created");
                }
                statement.setInt(1, getRoleId(role));
                statement.setInt(2, userId);
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

        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public ArrayList<UserRoles> getAll() throws SQLException, AwsSecretsManagerException {
        String query = GET_ROLES;
        ArrayList<UserRoles> userRolesList = new ArrayList<UserRoles>();
        try (Connection connection = DatabaseManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                UserRoles role = (UserRoles) AppConfig.getInstance().getUserManagementAbstractFactory().getUserRoleModel();
                role.setId(result.getInt("id"));
                role.setRole(result.getString("role"));
                userRolesList.add(role);

            }
        } catch (SQLException e) {

            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        return userRolesList;
    }

}
