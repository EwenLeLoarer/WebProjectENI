package fr.eni.ENI_enchere.bo;

import java.util.Date;

public class Enchere {
    
    private int noArticle;
    private String nomArticle;
    private String description;
    private int prixActuel;   // correspond à COALESCE(MAX(e.montant_enchere), a.prix_initial)
    private int prixInitial;   // valeur initiale
    private Date dateFin;
    private String vendeur;

    public Enchere(int noArticle, String nomArticle, String description, int prixActuel, Date dateFin, String vendeur) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.prixActuel = prixActuel;
        this.dateFin = dateFin;
        this.vendeur = vendeur;
    }

    // Getters et Setters
    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrixActuel() {
        return prixActuel;
    }

    public void setPrixActuel(int prixActuel) {
        this.prixActuel = prixActuel;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getVendeur() {
        return vendeur;
    }

    public void setVendeur(String vendeur) {
        this.vendeur = vendeur;
    }
}
