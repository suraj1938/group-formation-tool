package com.app.group15.Persistence;


import java.sql.SQLException;
import java.util.List;

import com.app.group15.ExceptionHandler.AwsSecretsManagerException;

@SuppressWarnings("hiding")
public interface IDao<T> {

    <T extends Persistence> T get(int id) throws SQLException, AwsSecretsManagerException;

    <T extends Persistence> List<T> getAll() throws SQLException, AwsSecretsManagerException;

    <T extends Persistence> int save(T t) throws SQLException, AwsSecretsManagerException;

    <T extends Persistence> void update(T t, int id) throws SQLException, AwsSecretsManagerException;

    <T extends Persistence> void delete(int id) throws SQLException, AwsSecretsManagerException;


}
