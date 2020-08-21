package com.app.group15.PasswordPolicyManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.InvokeStoredProcedure;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PasswordPolicyDao extends PasswordPolicyAbstractDao {

    IPasswordPolicyAbstractFactory passwordPolicyAbstractFactory = AppConfig.getInstance().getPasswordPolicyAbstractFactory();

    @Override
    public List<PasswordPolicy> getAll() throws SQLException, AwsSecretsManagerException {

        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spFindAllPasswordPolicy()");
            ResultSet results = invokeStoredProcedure.executeWithResults();
            List<PasswordPolicy> listOfPolicy = new ArrayList<>();
            if (results != null) {
                while (results.next()) {
                    PasswordPolicy policy = (PasswordPolicy) passwordPolicyAbstractFactory.getPasswordPolicyModel();
                    policy.setPolicyId(results.getInt("id"));
                    policy.setPolicyName(results.getString("policy_name"));
                    policy.setPolicyDescription(results.getString("policy_desc"));
                    policy.setIs_active(results.getBoolean("is_active"));
                    policy.setPolicyValue(results.getString("policy_value"));
                    listOfPolicy.add(policy);
                }
            }
            return listOfPolicy;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            invokeStoredProcedure.closeConnection();
        }

    }

    @Override
    public List<PasswordPolicy> getActivePasswordPolicy() throws SQLException, AwsSecretsManagerException {

        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spFindActivePasswordPolicy()");
            ResultSet results = invokeStoredProcedure.executeWithResults();
            List<PasswordPolicy> listOfPolicy = new ArrayList<>();
            if (results != null) {
                while (results.next()) {
                    PasswordPolicy policy = (PasswordPolicy) passwordPolicyAbstractFactory.getPasswordPolicyModel();
                    policy.setPolicyId(results.getInt("id"));
                    policy.setPolicyName(results.getString("policy_name"));
                    policy.setPolicyDescription(results.getString("policy_desc"));
                    policy.setIs_active(results.getBoolean("is_active"));
                    policy.setPolicyValue(results.getString("policy_value"));
                    listOfPolicy.add(policy);
                }
            }
            return listOfPolicy;
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            invokeStoredProcedure.closeConnection();
        }

    }

}
