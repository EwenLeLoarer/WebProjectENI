package fr.eni.ENI_enchere.service;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.repository.ArticleRepositorySQL;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnchereService {

    private final ArticleRepositorySQL repository;

    public EnchereService(ArticleRepositorySQL repository) {
        this.repository = repository;
    }

    // Retourne toutes les enchères actives (statut = 1)
    public List<Article> getAllEncheres() {
        return repository.getAllEncheres();
    }

    // Retourne les enchères dans lesquelles l'utilisateur a déjà enchéri (mes enchères en cours)
    public List<Article> getMesEncheresEnCours(String pseudo, String search, String categorie) {
        return repository.getMesEncheresEnCours(pseudo);
    }

    // Retourne les enchères remportées par l'utilisateur (exemple : statut = 2)
    public List<Article> getMesEncheresRemportees(String pseudo, String search, String categorie) {
        return repository.getMesEncheresRemportees(pseudo);
    }

    // Retourne les ventes actives créées par l'utilisateur
    public List<Article> getMesVentesEnCours(String pseudo, String search, String categorie) {
        return repository.getMesVentesEnCours(pseudo);
    }

    // Retourne les ventes non débutées créées par l'utilisateur (statut = 0)
    public List<Article> getMesVentesNonDebutees(String pseudo, String search, String categorie) {
        return repository.getMesVentesNonDebutees(pseudo);
    }

    // Retourne les ventes terminées créées par l'utilisateur (statut = 2)
    public List<Article> getMesVentesTerminees(String pseudo, String search, String categorie) {
        return repository.getMesVentesTerminees(pseudo);
    }
    
    /**
     * Tâche planifiée : tous les jours à minuit, on active les enchères qui démarrent aujourd’hui.
     */
    @Scheduled(cron = "0 0 10 * * *") // S'exécute chaque jour à 00:00:00
    public void activerEncheresDuJour() {
        LocalDate today = LocalDate.now();
        List<Article> articles = repository.findArticlesByDateDebutEncheresAndStatut(today, 0);

        for (Article article : articles) {
            article.setStatut(1); // Passer en statut "EN COURS"
            repository.save(article);
        }

        System.out.println("Enchères activées pour le " + today + " : " + articles.size());
    }
    
    /**
     * Tâche planifiée : tous les jours à minuit, on clôture les enchères dont la date de fin est atteinte.
     */
    @Scheduled(cron = "0 0 10 * * *") // S'exécute chaque jour à 00:00:00
    public void cloturerEncheresExpirees() {
        LocalDate today = LocalDate.now();
        List<Article> articles = repository.findArticlesByDateFinEncheresAndStatut(today, 1);

        for (Article article : articles) {
            article.setStatut(2); // Passer en statut "CLOTURÉE"
            repository.save(article);
        }

        System.out.println("Enchères clôturées pour le " + today + " : " + articles.size());
    }

}
