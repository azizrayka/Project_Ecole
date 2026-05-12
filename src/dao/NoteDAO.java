package dao;
import database.DatabaseConnection;
import module.Note;
import java.sql.*;

public class NoteDAO implements BaseDAO<Note> {
    private Connection connection;
    public NoteDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Could not connect to database in NoteDAO");
        }
    }
    @Override
    public boolean add(Note note) {
        String sql = "INSERT INTO note (id_etu, nom_matiere, note, id_prof) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, note.getId_etu());
            ps.setString(2, note.getNom_matiere());
            ps.setDouble(3, note.getValeur());
            ps.setInt(4, note.getId_prof());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error NoteDAO.add: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean update(Note note) {
        String sql = "UPDATE note SET note = ?, id_prof = ? WHERE id_etu = ? AND nom_matiere = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, note.getValeur());
            ps.setInt(2, note.getId_prof());
            ps.setInt(3, note.getId_etu());
            ps.setString(4, note.getNom_matiere());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error NoteDAO.update: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean delete(Note note) {
        String sql = "DELETE FROM note WHERE id_etu = ? AND nom_matiere = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, note.getId_etu());
            ps.setString(2, note.getNom_matiere());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error NoteDAO.delete: " + e.getMessage());
            return false;
        }
    }

}