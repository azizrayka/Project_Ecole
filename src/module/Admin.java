package module;

public class Admin extends Person {
    private int id_adm,id_person,bureau;
    public Admin(int id_adm, int id_person, int bureau) {
        this.id_adm = id_adm;
        this.id_person = id_person;
        this.bureau = bureau;
    }
    public Admin(int id, String nom, String prenom, String dn, String email, String role, String mtp, int id_adm, int id_person, int bureau) {
        super(id, nom, prenom, dn, email, role, mtp);
        this.id_adm = id_adm;
        this.id_person = id_person;
        this.bureau = bureau;
    }
    public int getId_adm() {
        return id_adm;
    }

    public void setId_adm(int id_adm) {
        this.id_adm = id_adm;
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public int getBureau() {
        return bureau;
    }

    public void setBureau(int bureau) {
        this.bureau = bureau;
    }
}