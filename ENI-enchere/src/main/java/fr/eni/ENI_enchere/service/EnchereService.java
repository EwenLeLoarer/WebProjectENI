package fr.eni.ENI_enchere.service;

import java.util.List;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.bo.Enchere;

public interface EnchereService {

	List<Article> getAllEncheres();

	List<Article> getMesEncheresEnCours(String pseudo, String search, String categorie);

	List<Article> getMesEncheresRemportees(String pseudo, String search, String categorie);

	List<Article> getMesVentesEnCours(String pseudo, String search, String categorie);

	List<Article> getMesVentesNonDebutees(String pseudo, String search, String categorie);

	List<Article> getMesVentesTerminees(String pseudo, String search, String categorie);
	
    String getPseudoLastMiseByIdEnchere(String idEnchere);
    
    void saveEnchere(Enchere enchere);

	/**
	 * Tâche planifiée : tous les jours à minuit, on clôture les enchères dont la date de fin est atteinte.
	 */
	void cloturerEncheresExpirees();

	/**
	 * Tâche planifiée : tous les jours à minuit, on active les enchères qui démarrent aujourd’hui.
	 */
	void activerEncheresDuJour();
    

}
