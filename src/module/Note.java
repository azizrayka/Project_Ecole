package module;

public class Note {
    private int id_etu;
    private String nom_matiere;
    private double valeur;
    private int id_prof;
    public Note(int id_etu, String nom_matiere, double valeur, int id_prof) {
        this.id_etu      = id_etu;
        this.nom_matiere = nom_matiere;
        this.valeur      = valeur;
        this.id_prof     = id_prof;
    }
    public int    getId_etu(){
        return id_etu; }
    public String getNom_matiere(){
        return nom_matiere; }
    public double getValeur(){
        return valeur; }
    public int    getId_prof(){
        return id_prof; }
    public void setId_etu(int id_etu){
        this.id_etu      = id_etu; }
    public void setNom_matiere(String m){
        this.nom_matiere = m; }
    public void setValeur(double valeur){
        this.valeur      = valeur; }
    public void setId_prof(int id_prof){
        this.id_prof     = id_prof; }
}