package com.esprit.financialhub_desktop.tools;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
    private final String URL = "jdbc:mysql://localhost:3306/oussna_bank_fx";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private Connection connection;
    private static MyConnection instance;

    private MyConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully!");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database!");
            System.out.println(e.getMessage());
        }
    }

    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Database connection reestablished successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error checking or reestablishing the database connection!");
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error closing the database connection!");
            System.out.println(e.getMessage());
        }
    }
}
