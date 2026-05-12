package  module;

public class Enseignant extends Person {
    private int id_prof;
    private int id_person;
    private String speciality;
    private int id_etd;
    private double note;

    public Enseignant(int id, String nom, String prenom, String dn, String email, String role, String mtp, int id_prof, int id_person, String speciality) {
        super(id, nom, prenom, dn, email, role, mtp);
        this.id_prof = id_prof;
        this.id_person = id_person;
        this.speciality = speciality;
    }
    public Enseignant(int id_etd, String speciality, double note, int id_prof) {
        super();
        this.id_etd = id_etd;
        this.speciality = speciality;
        this.note = note;
        this.id_prof = id_prof;
    }
    public int getId_etd() {
        return id_etd;
    }

    public void setId_etd(int id_etd) {
        this.id_etd = id_etd;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public int getId_prof() {
        return id_prof;
    }

    public void setId_prof(int id_prof) {
        this.id_prof = id_prof;
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_peron) {
        this.id_person = id_peron;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}