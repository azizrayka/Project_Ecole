package  module;

public class Etudiant extends Person{
    private int id_etu,id_person;
    private double moy;
    private String niveau;

    public Etudiant(int id, String nom, String prenom, String dn, String email, String role, String mtp, int id_etu, int id_person, double moy, String niveau) {
        super(id, nom, prenom, dn, email, role, mtp);
        this.id_etu = id_etu;
        this.id_person = id_person;
        this.moy = moy;
        this.niveau = niveau;
    }

    public Etudiant(int id_etu, int id_person, double moy, String niveau) {
        this.id_etu = id_etu;
        this.id_person = id_person;
        this.moy = moy;
        this.niveau = niveau;
    }

    public int getId_etu() {
        return id_etu;
    }

    public void setId_etu(int id_etu) {
        this.id_etu = id_etu;
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public double getMoy() {
        return moy;
    }

    public void setMoy(double moy) {
        this.moy = moy;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}