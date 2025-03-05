package fr.eni.ENI_enchere.service;

import java.util.List;

import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.bo.Categorie;

public interface NouvelleVenteService {

	List<Categorie> getAllCategories();

	List<Adresse> getAllAdresses();

	void creerArticle(Article article);

	/**
	 * Tâche planifiée : tous les jours à minuit, on active les enchères qui démarrent aujourd’hui.
	 */
	void activerEncheresDuJour();

	/**
	 * Tâche planifiée : tous les jours à minuit, on clôture les enchères dont la date de fin est atteinte.
	 */
	void cloturerEncheresExpirees();

}
