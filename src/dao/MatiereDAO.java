package dao;
import database.DatabaseConnection;
import module.Matiere;
import java.sql.*;

public class MatiereDAO implements BaseDAO<Matiere> {
    private Connection connection;
    public MatiereDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Could not connect to database in MatiereDAO");
        }
    }
    @Override
    public boolean add(Matiere m) {
        String sql = "INSERT INTO matiere (nom, coeff, id_prof) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setDouble(2, m.getCoeff());
            if (m.getId_prof() > 0) ps.setInt(3, m.getId_prof());
            else ps.setNull(3, Types.INTEGER);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error MatiereDAO.add: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean update(Matiere m) {
        String sql = "UPDATE matiere SET coeff = ?, id_prof = ? WHERE nom = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, m.getCoeff());
            if (m.getId_prof() > 0) ps.setInt(2, m.getId_prof());
            else ps.setNull(2, Types.INTEGER);
            ps.setString(3, m.getNom());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error MatiereDAO.update: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean delete(Matiere m) {
        String sql = "DELETE FROM matiere WHERE nom = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error MatiereDAO.delete: " + e.getMessage());
            return false;
        }
    }

}