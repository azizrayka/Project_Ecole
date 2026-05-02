package module;

public abstract class Person {
    private int id;
    private String nom;
    private String prenom;
    private String dn;
    private String email;
    private String role;
    private String mtp;

    public Person(int id, String nom, String prenom, String dn, String email, String role, String mtp) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dn = dn;
        this.email = email;
        this.role = role;
        this.mtp = mtp;
    }

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMtp() {
        return mtp;
    }

    public void setMtp(String mtp) {
        this.mtp = mtp;
    }
}