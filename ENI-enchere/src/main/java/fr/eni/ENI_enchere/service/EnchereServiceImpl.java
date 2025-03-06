package fr.eni.ENI_enchere.service;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.bo.Enchere;
import fr.eni.ENI_enchere.repository.ArticleRepository;
import fr.eni.ENI_enchere.repository.EnchereRepository;
import fr.eni.ENI_enchere.repository.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {

	@Autowired
	private ArticleRepository repository;
	@Autowired
	private EnchereRepository enchereRepository;
	@Autowired
	private UtilisateurRepository userRepository;

	// Retourne toutes les enchères actives (statut = 1)
	@Override
	public List<Article> getAllEncheres() {

		return repository.getAllEncheres();
	}

	// Retourne les enchères dans lesquelles l'utilisateur a déjà enchéri (mes
	// enchères en cours)
	@Override
	public List<Article> getMesEncheresEnCours(String pseudo, String search, String categorie) {
		return repository.getMesEncheresEnCours(pseudo);
	}

	// Retourne les enchères remportées par l'utilisateur (exemple : statut = 2)
	@Override
	public List<Article> getMesEncheresRemportees(String pseudo, String search, String categorie) {
		return repository.getMesEncheresRemportees(pseudo);
	}

	// Retourne les ventes actives créées par l'utilisateur
	@Override
	public List<Article> getMesVentesEnCours(String pseudo, String search, String categorie) {
		return repository.getMesVentesEnCours(pseudo);
	}

	// Retourne les ventes non débutées créées par l'utilisateur (statut = 0)
	@Override
	public List<Article> getMesVentesNonDebutees(String pseudo, String search, String categorie) {
		return repository.getMesVentesNonDebutees(pseudo);
	}

	// Retourne les ventes terminées créées par l'utilisateur (statut = 2)
	@Override
	public List<Article> getMesVentesTerminees(String pseudo, String search, String categorie) {
		return repository.getMesVentesTerminees(pseudo);
	}

	@Override
	public String getPseudoLastMiseByIdEnchere(String idEnchere) {
		return this.enchereRepository.getPseudoLastMiseByIdEnchere(idEnchere);
	}

	@Override
	public void saveEnchere(Enchere enchere) {
		this.enchereRepository.saveEnchere(enchere);

	}

	/**
	 * Tâche planifiée : tous les jours à minuit, on active les enchères qui
	 * démarrent aujourd’hui.
	 */
	@Override
	@Scheduled(cron = "0 30 9 * * *") // S'exécute chaque jour à 00:00:00
	public void activerEncheresDuJour() {
		LocalDate today = LocalDate.now();
		List<Article> articles = repository.getAllEncheres();

		for (Article article : articles) {
			if (article.getDateDebutEncheres().isAfter(today) && article.getStatut() == 0) {
				article.setStatut(1); // Passer en statut "EN COURS"
				repository.save(article);
			}
		}

		System.out.println("Enchères activées pour le " + today + " : " + articles.size());
	}

	/**
	 * Tâche planifiée : tous les jours à minuit, on clôture les enchères dont la
	 * date de fin est atteinte.
	 */
	@Override
	@Scheduled(cron = "0 30 9 * * *") // S'exécute chaque jour à 00:00:00
	public void cloturerEncheresExpirees() {
		LocalDate today = LocalDate.now();
		List<Article> articles = repository.getAllEncheres();

		for (Article article : articles) {
			if (article.getDateFinEncheres().isBefore(today) && (article.getStatut() == 1)) {
				article.setStatut(2); // Passer en statut "CLOTURÉE"
				repository.save(article);
				String winner = this.enchereRepository.getPseudoLastMiseByIdEnchere(article.getNo_article().toString());
				if(winner != "" && winner != null) {
					this.userRepository.addCreditToUserByPseudo(winner, article.getPrixVente());
				}
			}
		}
		
		
		
		System.out.println("Enchères clôturées pour le " + today + " : " + articles.size());
	}

}
