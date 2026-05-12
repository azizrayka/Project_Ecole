package module;

public class Matiere {
    private String nom;
    private double coeff;
    private int id_prof;
    public Matiere(String nom, double coeff, int id_prof) {
        this.nom     = nom;
        this.coeff   = coeff;
        this.id_prof = id_prof;
    }
    public String getNom(){
        return nom; }
    public double getCoeff(){
        return coeff; }
    public int    getId_prof(){
        return id_prof; }
    public void setNom(String nom){
        this.nom     = nom; }
    public void setCoeff(double coeff){
        this.coeff   = coeff; }
    public void setId_prof(int id_prof){
        this.id_prof = id_prof; }
}