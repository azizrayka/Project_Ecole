package dao;

import database.DatabaseConnection;

import java.sql.*;

public class PersonDAO {
    private Connection connection;
    public PersonDAO() throws SQLException {
        try {
            this.connection = DatabaseConnection.getConnection();
            if (this.connection == null) {
                throw new SQLException("Failed to obtain database connection");
            }
            System.out.println("PersonDAO initialized successfully");
        } catch (SQLException e) {
            System.out.println("Could not connect to database in PersonDAO: " + e.getMessage());
            throw e;
        }
    }
    public int[] login(String email, String password) {
        if (connection == null) {
            System.out.println("Error: Database connection is null in login method");
            return null;
        }

        String sql = "SELECT id, role FROM person WHERE email = ? AND mtp = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role").toLowerCase();
                int roleCode = switch (role) {
                    case "admin"      -> 0;
                    case "enseignant" -> 1;
                    case "etudiant"   -> 2;
                    default           -> -1;
                };
                return new int[]{id, roleCode};
            }
        } catch (SQLException e) {
            System.out.println("Error PersonDAO.login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public int add(String nom, String prenom, String dn, String email, String role, String mtp) {
        if (connection == null) {
            System.out.println("Error: Database connection is null in add method");
            return -1;
        }
        String sql = "INSERT INTO person (nom, prenom, dn, email, role, mtp) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, dn);
            ps.setString(4, email);
            ps.setString(5, role);
            ps.setString(6, mtp);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error PersonDAO.add: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    public boolean emailExists(String email) {
        if (connection == null) {
            System.out.println("Error: Database connection is null in emailExists method");
            return false;
        }
        String sql = "SELECT id FROM person WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error PersonDAO.emailExists: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("PersonDAO connection closed");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}