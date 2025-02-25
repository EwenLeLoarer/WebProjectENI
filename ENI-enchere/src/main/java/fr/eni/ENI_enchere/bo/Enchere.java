package fr.eni.ENI_enchere.bo;

import java.time.LocalDateTime;

public class Enchere {
    private int id;
    private String nomArticle;
    private String description;
    private LocalDateTime dateFin;
    private double prixInitial;
    private String vendeur;

    public Enchere(int id, String nomArticle, String description, LocalDateTime dateFin, double prixInitial, String vendeur) {
        this.id = id;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateFin = dateFin;
        this.prixInitial = prixInitial;
        this.vendeur = vendeur;
    }

    // Getters
    public int getId() { return id; }
    public String getNomArticle() { return nomArticle; }
    public String getDescription() { return description; }
    public LocalDateTime getDateFin() { return dateFin; }
    public double getPrixInitial() { return prixInitial; }
    public String getVendeur() { return vendeur; }
}
