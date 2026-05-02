package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {
    private Connection connection;
    public EtudiantDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Could not connect to database in DAO");
        }
    }
    public List<Object[]> getEnseignants(int id_etd) throws SQLException, ClassNotFoundException {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT DISTINCT p.nom, p.prenom, n.nom_matiere
        FROM person p
        JOIN enseignant en ON en.id_person = p.id
        JOIN matiere m ON m.id_prof = en.id_prof
        JOIN note n ON n.nom_matiere = m.nom
        WHERE n.id_etu = ?
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = {
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_matiere") // ← was "matiere"
                };
                list.add(row);
            }
        } catch (Exception e) {
            System.out.println("Error getEnseignants: " + e.getMessage());
        }
        return list;
    }
    public List<Object[]> getMatieres(int id_etd) throws SQLException, ClassNotFoundException {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT DISTINCT m.nom, m.coeff
        FROM matiere m
        JOIN note n ON n.nom_matiere = m.nom
        WHERE n.id_etu = ?
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = {
                        rs.getString("nom"),
                        rs.getDouble("coeff")
                };
                list.add(row);
            }
        } catch (Exception e) {
            System.out.println("Error getMatieres: " + e.getMessage());
        }
        return list;
    }
    public List<Object[]> getNotes(int id_etd) throws SQLException, ClassNotFoundException {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT DISTINCT m.nom, n.note
        FROM matiere m
        JOIN note n ON n.nom_matiere = m.nom
        WHERE n.id_etu = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = {
                        rs.getString("nom"),
                        rs.getDouble("note")
                };
                list.add(row);
            }
        }catch (Exception e) {
            System.out.println("Error getMatieres: " + e.getMessage());
        }
        return list;
    }
}