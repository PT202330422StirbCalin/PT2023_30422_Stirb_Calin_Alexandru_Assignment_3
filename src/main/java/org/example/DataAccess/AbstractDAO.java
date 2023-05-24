package org.example.DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.example.Connection.*;


/**
 * The AbstractDAO class provides a generic implementation for database operations using JDBC.
 *
 * @param <T> the type of the entity being handled by the DAO
 */

public class AbstractDAO<T> {
    private final Class<T> type;
    /**
     * Constructs an AbstractDAO object.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates a SELECT query to retrieve data based on a specific field.
     *
     * @param field the field name
     * @return the SELECT query string
     */
    public final String createSelectQuery(String field) {
        String tableName = "\"" + type.getSimpleName() + "\"";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("* ");
        sb.append("FROM ");
        sb.append(tableName);
        sb.append("WHERE ");
        sb.append("\"" + field + "\"");
        sb.append(" = ?");
        return sb.toString();
    }


    /**
     * Creates a SELECT query to retrieve all data from the table.
     *
     * @return the SELECT query string
     */
     public final String createSelectAll(){
         String tableName = "\"" + type.getSimpleName() + "\"";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(tableName);
        return sb.toString();
    }


    /**
     * Creates a DELETE query to delete data based on a specific field.
     *
     * @param field the field name
     * @param value the field value
     * @return the DELETE query string
     */
    public final String createDeleteQuery(String field, Object value){
        String tableName = "\"" + type.getSimpleName() + "\"";
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append("FROM ");
        sb.append(tableName);
        sb.append("WHERE ");
        sb.append("\"" + field + "\"");
        sb.append(" =?");
        return sb.toString();
    }

    /**
     * Creates an UPDATE query to update a specific column.
     *
     * @param columnName the column name to be updated
     * @param value      the new value for the column
     * @return the UPDATE query string
     */
    public final String createUpdateQuery(String columnName, Object value){
        String tableName = "\"" + type.getSimpleName() + "\"";
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(tableName);
        sb.append("SET ");
        sb.append(columnName);
        sb.append("=? ");
        sb.append("WHERE ");
        sb.append(columnName);
        sb.append("=?");
        return  sb.toString();
    }

    /**
     * Creates an INSERT query to insert a new entity into the table.
     *
     * @param entity the entity to be inserted
     * @return the INSERT query string
     */
    public final String createInsertQuery(T entity){
        String tableName = "\"" + type.getSimpleName() + "\"";
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append(" (");
        Field[] fields = type.getDeclaredFields();
        for(int i = 0; i< fields.length;i++){
            Field field = fields[i];
            String fieldName = "\"" + field.getName() + "\"";
            sb.append(fieldName);
            if(i< fields.length-1){
                sb.append(", ");
            }
        }
        sb.append(") VALUES(");
        for(int i = 0; i< fields.length;i++){
            sb.append("?");
            if(i< fields.length-1){
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
    /**
     * Retrieves a list of entities that match a specific column name and value.
     *
     * @param columnName the column name to search for
     * @param value      the value to match
     * @return a list of entities that match the criteria
     */

    public final List<T> findBy(String columnName, Object value){
        String query = createSelectQuery(columnName);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1,value);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return Collections.emptyList();
    }

    /**
     * Inserts a new entity into the table.
     * @param entity the entity to be inserted
     */
    public final void insert(T entity){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery(entity);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            Field[] fields = type.getDeclaredFields();

            for(int i =0;i< fields.length;i++){
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(entity);
                statement.setObject(i+1,value);
            }
            int result = statement.executeUpdate();
        }catch(SQLException | IllegalAccessException e){
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Deletes data from the table based on a specific column name and value.
     *
     * @param colName the column name to search for
     * @param field   the value to match
     */
    public final void delete(String colName, Object field){
        String query = createDeleteQuery(colName,field);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1,field);
            int result = statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
    /**
     * Retrieves all entities from the table.
     *
     * @return a list of all entities in the table
     */
    public final List<T>findAll() {
        Vector<T> list = new Vector<T>();
        String query = createSelectAll();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return Collections.emptyList();
    }
    /**
     * Updates a specific column with a new value.
     *
     * @param columnName the column name to update
     * @param value      the new value for the column
     * @param original   the original value of the column
     */
    public void update(String columnName, Object value, Object original){
        String query = createUpdateQuery(columnName, "?");
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(2,original);
            statement.setObject(1,value);
            int result = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Creates a list of entities from a ResultSet.
     * @param resultSet the ResultSet containing the data
     * @return a list of entities created from the ResultSet
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor constructor : ctors) {
            ctor = constructor;
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }
}
