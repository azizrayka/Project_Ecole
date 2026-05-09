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
    private int resolveIdEtu(int id_person) {
        String sql = "SELECT id_etu FROM etudiant WHERE id_person = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_person);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_etu");
            }
        } catch (SQLException e) {
            System.out.println("Error resolveIdEtu: " + e.getMessage());
        }
        return -1;
    }
    public double getMoyenne(int id_person) {
        int id_etu = resolveIdEtu(id_person);
        if (id_etu == -1) return 0;
        String sql = """
            SELECT SUM(n.note * m.coeff) / SUM(m.coeff) AS moyenne
            FROM note n
            JOIN matiere m ON n.nom_matiere = m.nom
            WHERE n.id_etu = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etu);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("moyenne");
            }
        } catch (Exception e) {
            System.out.println("Error getMoyenne: " + e.getMessage());
        }
        return 0;
    }
    public boolean updateMoyenne(int id_person) {
        int id_etu = resolveIdEtu(id_person);
        if (id_etu == -1) return false;
        String sql = """
            UPDATE etudiant SET moy = (
                SELECT SUM(n.note * m.coeff) / SUM(m.coeff)
                FROM note n
                JOIN matiere m ON n.nom_matiere = m.nom
                WHERE n.id_etu = ?
            )
            WHERE id_etu = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etu);
            ps.setInt(2, id_etu);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error updateMoyenne: " + e.getMessage());
            return false;
        }
    }
    public List<Object[]> getEnseignants(int id_person) throws SQLException, ClassNotFoundException {
        List<Object[]> list = new ArrayList<>();
        int id_etu = resolveIdEtu(id_person);
        if (id_etu == -1) return list;
        String sql = """
            SELECT DISTINCT p.nom, p.prenom, n.nom_matiere
            FROM person p
            JOIN enseignant en ON en.id_person = p.id
            JOIN matiere m ON m.id_prof = en.id_prof
            JOIN note n ON n.nom_matiere = m.nom
            WHERE n.id_etu = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etu);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("nom_matiere")
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Error getEnseignants: " + e.getMessage());
        }
        return list;
    }
    public List<Object[]> getMatieres(int id_person) throws SQLException, ClassNotFoundException {
        List<Object[]> list = new ArrayList<>();
        int id_etu = resolveIdEtu(id_person);
        if (id_etu == -1) return list;
        String sql = """
            SELECT DISTINCT m.nom, m.coeff
            FROM matiere m
            JOIN note n ON n.nom_matiere = m.nom
            WHERE n.id_etu = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etu);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{ rs.getString("nom"), rs.getDouble("coeff") });
                }
            }
        } catch (Exception e) {
            System.out.println("Error getMatieres: " + e.getMessage());
        }
        return list;
    }
    public List<Object[]> getNotes(int id_person) throws SQLException, ClassNotFoundException {
        List<Object[]> list = new ArrayList<>();
        int id_etu = resolveIdEtu(id_person);
        if (id_etu == -1) return list;
        String sql = """
            SELECT m.nom, n.note
            FROM matiere m
            JOIN note n ON n.nom_matiere = m.nom
            WHERE n.id_etu = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etu);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{ rs.getString("nom"), rs.getDouble("note") });
                }
            }
        } catch (Exception e) {
            System.out.println("Error getNotes: " + e.getMessage());
        }
        return list;
    }
}