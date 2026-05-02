package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private Connection connection;
    public AdminDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Could not connect to database in DAO");
        }
    }
    public List<Object[]> getEtudiants() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT e.id_etu, p.nom, p.prenom, e.moy, e.niveau
            FROM etudiant e
            JOIN person p ON p.id = e.id_person
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Object[]{
                        rs.getInt("id_etu"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDouble("moy"),
                        rs.getString("niveau")
                });
            }
        } catch (Exception e) {
            System.out.println("Error getEtudiants: " + e.getMessage());
        }
        return list;
    }
    public boolean addEtudiant(int id_etu, double moy, String niveau) {
        String sql = "INSERT INTO etudiant (id_etu, moy, niveau) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etu);
            ps.setDouble(2, moy);
            ps.setString(3, niveau);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteEtudiant(int id_etu) {
        String sql = "DELETE FROM etudiant WHERE id_etu = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etu);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Object[]> getEnseignants() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT en.id_prof, p.nom, p.prenom, en.speciality
            FROM enseignant en
            JOIN person p ON p.id = en.id_person
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Object[]{
                        rs.getInt("id_prof"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("speciality")
                });
            }
        } catch (Exception e) {
            System.out.println("Error getEnseignants: " + e.getMessage());
        }
        return list;
    }
    public boolean addEnseignant(int id_prof, String speciality) {
        String sql = "INSERT INTO enseignant (id_prof, speciality) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_prof);
            ps.setString(2, speciality);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteEnseignant(int id_prof) {
        String sql = "DELETE FROM enseignant WHERE id_prof = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_prof);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean affecterMatiereEnseignant(int id_prof, String nom_matiere) {
        String sql = "UPDATE matiere SET id_prof = ? WHERE nom = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_prof);
            ps.setString(2, nom_matiere);
            int rows = ps.executeUpdate();
            System.out.println("affecterMatiereEnseignant rows affected: " + rows);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean affecterEtudiantMatiere(int id_etu, String nom_matiere) {
        String sql = "INSERT INTO note (id_etu, nom_matiere, note) VALUES (?, ?, 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etu);
            ps.setString(2, nom_matiere);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Object[]> getMatieres() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT nom, coeff FROM matiere";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(new Object[]{ rs.getString("nom"), rs.getDouble("coeff") });
        } catch (Exception e) {
            System.out.println("Error getMatieres: " + e.getMessage());
        }
        return list;
    }
}