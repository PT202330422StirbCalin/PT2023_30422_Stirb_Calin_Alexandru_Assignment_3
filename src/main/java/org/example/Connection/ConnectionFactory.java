package org.example.Connection;

import java.sql.*;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=tables";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";
    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(){
        return singleInstance.createConnection();
    }

    public static void close (Connection connection) {
        try {

            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void close(Statement statement) {
        try {
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void close(ResultSet resultSet) {
        try {
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
