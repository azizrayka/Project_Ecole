package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ecole";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789@Aziz";
    private static Connection instance = null;

    public static Connection getConnection() throws SQLException {
        if (instance == null || instance.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // force driver load
                instance = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("DB Connection established successfully");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL Driver not found: " + e.getMessage());
            }
        }
        return instance;
    }
}