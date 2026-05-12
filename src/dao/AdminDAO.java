package dao;
import database.DatabaseConnection;
import java.sql.*;
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
    public int addPerson(String nom, String prenom, String dn, String email, String role, String mtp) {
        String sql = "INSERT INTO person (nom, prenom, dn, email, role, mtp) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, dn);
            ps.setString(4, email);
            ps.setString(5, role);
            ps.setString(6, mtp);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public boolean deletePerson(int personId) {
        String sql = "DELETE FROM person WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, personId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Object[]> getEtudiants() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT e.id_etu, p.nom, p.prenom, p.dn, p.email, e.niveau FROM etudiant e JOIN person p ON e.id_person = p.id";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[]{rs.getInt("id_etu"), rs.getString("nom"), rs.getString("prenom"), rs.getString("dn"), rs.getString("email"), rs.getString("niveau")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean addEtudiant(int personId, String niveau) {
        String sql = "INSERT INTO etudiant (id_person, niveau, moy) VALUES (?, ?,0)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, personId);
            ps.setString(2, niveau);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteEtudiant(int id_etu) {
        int personId = getPersonIdFromEtudiant(id_etu);
        if (personId == -1) return false;
        String sqlChild = "DELETE FROM etudiant WHERE id_etu = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlChild)) {
            ps.setInt(1, id_etu);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return deletePerson(personId);
    }
    private int getPersonIdFromEtudiant(int id_etu) {
        String sql = "SELECT id_person FROM etudiant WHERE id_etu = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_etu);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_person");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public List<Object[]> getEnseignants() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT en.id_prof, p.nom, p.prenom, p.email FROM enseignant en JOIN person p ON p.id = en.id_person";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{rs.getInt("id_prof"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean addEnseignant(int personId, String speciality) {
        String sql = "INSERT INTO enseignant (id_person, speciality) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, personId);
            ps.setString(2, speciality);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteEnseignant(int id_prof) {
        int personId = getPersonIdFromEnseignant(id_prof);
        if (personId == -1) return false;
        String sqlChild = "DELETE FROM enseignant WHERE id_prof = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlChild)) {
            ps.setInt(1, id_prof);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return deletePerson(personId);
    }
    private int getPersonIdFromEnseignant(int id_prof) {
        String sql = "SELECT id_person FROM enseignant WHERE id_prof = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_prof);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_person");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public List<Object[]> getMatieres() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT nom, coeff FROM matiere";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(new Object[]{ rs.getString("nom"), rs.getDouble("coeff") });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean affecterMatiereEnseignant(int id_prof, String nom_matiere) {
        String sql = "UPDATE matiere SET id_prof = ? WHERE nom = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_prof);
            ps.setString(2, nom_matiere);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}