package dao;
import database.DatabaseConnection;
import module.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAO {
    private Connection connection;
    private final NoteDAO noteDAO = new NoteDAO();

    public EnseignantDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Could not connect to database in EnseignantDAO");
        }
    }
    public boolean saveNote(int id_etu, String nom_matiere, double valeur, int id_prof) {
        Note note = new Note(id_etu, nom_matiere, valeur, id_prof);
        return noteDAO.update(note) || noteDAO.add(note);
    }
    public boolean resetNote(int id_etu, String nom_matiere, int id_prof) {
        return noteDAO.update(new Note(id_etu, nom_matiere, 0, id_prof));
    }
    public List<Object[]> getStudentsWithGrades(int id_prof) {
        List<Object[]> data = new ArrayList<>();
        String sql = """
            SELECT e.id_etu, p.nom, p.prenom, e.niveau, m.nom AS matiere, n.note
            FROM etudiant e
            JOIN person   p ON e.id_person  = p.id
            JOIN note     n ON e.id_etu      = n.id_etu
            JOIN matiere  m ON n.nom_matiere = m.nom
            WHERE m.id_prof = ?
            ORDER BY p.nom, p.prenom, m.nom
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_prof);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.add(new Object[]{
                            rs.getInt("id_etu"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("niveau"),
                            rs.getString("matiere"),
                            rs.getDouble("note")
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Error EnseignantDAO.getStudentsWithGrades: " + e.getMessage());
        }
        return data;
    }
    public List<Object[]> getMatieres(int id_prof) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT nom, coeff FROM matiere WHERE id_prof = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_prof);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{ rs.getString("nom"), rs.getDouble("coeff") });
                }
            }
        } catch (Exception e) {
            System.out.println("Error EnseignantDAO.getMatieres: " + e.getMessage());
        }
        return list;
    }
    public int resolveIdProf(int id_person) {
        String sql = "SELECT id_prof FROM enseignant WHERE id_person = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_person);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_prof");
            }
        } catch (Exception e) {
            System.out.println("Error resolveIdProf: " + e.getMessage());
        }
        return -1;
    }
}