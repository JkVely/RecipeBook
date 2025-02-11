package com.recipebook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLController {
    private Connection connection;
    private boolean connected = false;
    private String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=RECIPE_BOOK;integratedSecurity=true;encrypt=false;trustServerCertificate=true;username=PCPersonal/jkqui";

    public SQLController() {
        connected = start(this.connectionUrl);
    }

    private boolean start(String connectionUrl) {
        try {
            // Cargar el driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.print("Connecting to SQL Server ... ");
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Done.");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server JDBC Driver not found.");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.out.println();
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        if (isConnected()) {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } else {
            throw new SQLException("Not connected to the database.");
        }
    }

    public int executeUpdate(String query) throws SQLException {
        if (isConnected()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        } else {
            throw new SQLException("Not connected to the database.");
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}