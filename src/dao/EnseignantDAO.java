package dao;

import module.Enseignant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAO implements BaseDAO<Enseignant> {
    private Connection connection;

    public EnseignantDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Could not connect to database in EnseignantDAO");
        }
    }
    @Override
    public boolean add(Enseignant enseignant) {
        String sql = "INSERT INTO note (id_etu, nom_matiere, note, id_prof) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, enseignant.getId_etd());
            ps.setString(2, enseignant.getSpeciality());
            ps.setDouble(3, enseignant.getNote());
            ps.setInt(4, enseignant.getId_prof());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Note added by prof id=" + enseignant.getId_prof());
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error EnseignantDAO.add: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean update(Enseignant enseignant) {
        String sql = "UPDATE note SET note = ? WHERE id_etu = ? AND nom_matiere = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, enseignant.getNote());
            ps.setInt(2, enseignant.getId_etd());
            ps.setString(3, enseignant.getSpeciality());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Note updated: rows affected = " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error EnseignantDAO.update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean delete(Enseignant enseignant) {
        String sql = "DELETE FROM note WHERE id_etu = ? AND nom_matiere = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, enseignant.getId_etd());
            ps.setString(2, enseignant.getSpeciality());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Note reset to 0: rows affected = " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error EnseignantDAO.delete: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public List<Object[]> getAll(int id_prof) {
        List<Object[]> data = new ArrayList<>();
        String sql = """
        SELECT e.id_etu, p.nom, p.prenom, e.niveau, m.nom, n.note
        FROM etudiant e
        JOIN person p  ON e.id_person  = p.id
        JOIN note n    ON e.id_etu      = n.id_etu
        JOIN matiere m ON n.nom_matiere = m.nom
        WHERE m.id_prof = ?
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_prof);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.add(new Object[]{
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDouble(6)
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Error EnseignantDAO.getAll: " + e.getMessage());
            e.printStackTrace();
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
        }catch (Exception e) {
            System.out.println("Error getMatieres: " + e.getMessage());
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