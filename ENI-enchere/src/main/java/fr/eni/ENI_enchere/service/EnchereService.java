package fr.eni.ENI_enchere.service;

import java.util.List;

import fr.eni.ENI_enchere.bo.Article;

public interface EnchereService {

	List<Article> getAllEncheres();

	List<Article> getMesEncheresEnCours(String pseudo, String search, String categorie);

	List<Article> getMesEncheresRemportees(String pseudo, String search, String categorie);

	List<Article> getMesVentesEnCours(String pseudo, String search, String categorie);

	List<Article> getMesVentesNonDebutees(String pseudo, String search, String categorie);

	List<Article> getMesVentesTerminees(String pseudo, String search, String categorie);

}
