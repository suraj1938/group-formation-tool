package com.app.group15.QuestionManager;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;
import com.app.group15.Persistence.InvokeStoredProcedure;
import com.app.group15.Utility.GroupFormationToolLogger;

import java.sql.SQLException;
import java.util.logging.Level;

public class QuestionChoiceMapperDao extends QuestionChoiceMapperAbstractDao {

    @Override
    public void deleteByQuestionId(int questionId) throws SQLException, AwsSecretsManagerException {
        InvokeStoredProcedure invokeStoredProcedure = null;
        try {
            invokeStoredProcedure = new InvokeStoredProcedure("spDeleteCompleteQuestion(?)");

            invokeStoredProcedure.setParameter(1, questionId);
            invokeStoredProcedure.execute();
        } catch (SQLException e) {
            GroupFormationToolLogger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        } finally {
            assert invokeStoredProcedure != null;
            invokeStoredProcedure.closeConnection();
        }
    }
}
